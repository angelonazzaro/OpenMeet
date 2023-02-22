package com.openmeet.webapp.logicLayer;

import com.openmeet.shared.data.ban.Ban;
import com.openmeet.shared.data.ban.BanDAO;
import com.openmeet.shared.data.meeter.Meeter;
import com.openmeet.shared.data.meeter.MeeterDAO;
import com.openmeet.shared.helpers.ResponseHelper;
import com.openmeet.shared.utils.MultiMapList;
import com.openmeet.shared.utils.QueryJoinExecutor;
import com.openmeet.webapp.dataLayer.moderator.Moderator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Servlet that handles the ban functionality.
 *
 * @author Angelo Nazzaro
 */
public class BanServlet extends HttpServlet {

    private static final String from = "staff.openmeet@gmail.com";

    /**
     * Get the bans and displays them.
     *
     * @param req  The request object.
     * @param resp The response object.
     * @throws ServletException If an error occurs.
     * @throws IOException      If an error occurs.
     * @author Angelo Nazzaro
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("view", "bans");
        req.setAttribute("title", "Bans");
        req.setAttribute("heading", "Bans");

        // if banIdToUnban is set, delete the ban from the db
        if (req.getParameter("banIdToUnban") != null) {
            int banIdToUnBan = Integer.parseInt((String) req.getParameter("banIdToUnban"));
            BanDAO banDAO = new BanDAO((DataSource) getServletContext().getAttribute("DataSource"));

            try {
                banDAO.doDelete(String.format("%s = %d", Ban.BAN_ID, banIdToUnBan));
            } catch (SQLException e) {
                resp.sendRedirect(req.getContextPath() + "/ban");
                return;
            }
        }

        // Get Data
        QueryJoinExecutor qjx = new QueryJoinExecutor((DataSource) getServletContext().getAttribute("DataSource"));
        MultiMapList<String, String> data = new MultiMapList<>();

        try {
            data = qjx.doRetrivedByJoin(
                    String.format(
                            "SELECT %s.*, CONCAT(%s, ' ', %s) AS meeterfullName, %s " +
                                    "FROM %s JOIN %s ON %s = %s " +
                                    "ORDER BY %s DESC",
                            Ban.BAN, Meeter.MEETER_MEETER_NAME, Meeter.MEETER_MEETER_SURNAME, Meeter.MEETER_EMAIL,
                            Ban.BAN, Meeter.MEETER, Meeter.MEETER_ID, Ban.BAN_MEETER_ID, Ban.BAN_START_TIME));

        } catch (SQLException e) {
            resp.sendError(500, "Internal Server Error");
        }

        req.setAttribute("data", data);

        req.getRequestDispatcher("WEB-INF/index.jsp").forward(req, resp);
    }

    /**
     * Creates a new ban if the Meeter is not already banned.
     *
     * @param req  The request object.
     * @param resp The response object.
     * @throws IOException If an error occurs.
     * @author Angelo Nazzaro
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String description = req.getParameter("description");
        String strEndTime = req.getParameter("endTime");
        int meeterId = Integer.parseInt(req.getParameter("meeterId"));

        PrintWriter out = resp.getWriter();

        if (description.length() < 1 || description.length() > 255) {
            ResponseHelper.sendGenericError(out);
            return;
        }

        Timestamp endTime = null;

        if (strEndTime != null && strEndTime.length() > 0) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            try {
                Date date = sf.parse(strEndTime);
                endTime = new Timestamp(date.getTime());
            } catch (ParseException e) {
                resp.setContentType("application/json");
                ResponseHelper.sendGenericError(out);
                return;
            }
        }

        BanDAO banDAO = new BanDAO((DataSource) getServletContext().getAttribute("DataSource"));

        // if banId is set, modify the ban associated
        if (req.getParameter("banId") != null) {
            int banId = Integer.parseInt(req.getParameter("banId"));

            try {
                List<Ban> bans = banDAO.doRetrieveByCondition(String.format("%s = %d", Ban.BAN_ID, banId));

                if (!bans.isEmpty()) {
                    Ban ban = bans.get(0);

                    if (endTime != null && ban.getStartTime().after(endTime)) {
                        resp.setContentType("application/json");
                        ResponseHelper.sendGenericError(out);
                        return;
                    }

                    ban.setDescription(description);
                    ban.setEndTime(endTime);

                    if (banDAO.doSaveOrUpdate(ban)) {
                        resp.sendRedirect(String.valueOf(req.getRequestURL()));
                        return;
                    }
                }

            } catch (SQLException e) {
                resp.setContentType("application/json");
                ResponseHelper.sendGenericError(out);
                return;
            }

        }

        // Check if the meeter has a ban already in the same period or has been permanently banned
        try {
            List<Ban> meeterBans = banDAO.doRetrieveByCondition(String.format("%s = %d AND (%s IS NULL)",
                    Ban.BAN_MEETER_ID, meeterId, Ban.BAN_END_TIME));

            // user is already banned
            if (!meeterBans.isEmpty()) {
                HashMap<String, String> values = new HashMap<>();

                values.put("status", "error");
                values.put("msg", "The meeter is already banned!");

                resp.setContentType("application/json");
                ResponseHelper.sendGenericResponse(out, values);
                return;
            }

        } catch (SQLException e) {
            resp.setContentType("application/json");
            ResponseHelper.sendGenericError(out);
            return;
        }

        Moderator user = (Moderator) req.getSession(false).getAttribute("user");
        HashMap<String, String> ban = new HashMap<>();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        ban.put(Ban.BAN_MODERATOR_ID, String.valueOf(user.getId()));
        ban.put(Ban.BAN_DESCRIPTION, description);
        ban.put(Ban.BAN_MEETER_ID, String.valueOf(meeterId));
        ban.put(Ban.BAN_START_TIME, String.valueOf(currentTime));

        if (endTime != null) {

            if (endTime.before(currentTime)) {
                resp.setContentType("application/json");
                ResponseHelper.sendGenericError(out);
                return;
            }

            ban.put(Ban.BAN_END_TIME, String.valueOf(endTime));
        }

        try {
            if (banDAO.doSave(ban)) {
                out.write("Ban Saved");

                // Send email
                Properties properties = System.getProperties();
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");

                Session session = Session.getInstance(
                        properties,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(from, "tohybegjuqpwbzji");
                            }
                        });

                try {
                    Meeter meeter = new MeeterDAO((DataSource) getServletContext().getAttribute("DataSource"))
                            .doRetrieveByKey(String.valueOf(meeterId));

                    MimeMessage message = new MimeMessage(session);

                    message.addHeader("Content-type", "text/HTML; charset=UTF-8");
                    message.setFrom(new InternetAddress(from));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(meeter.getEmail()));
                    message.setSubject("Your account has been suspended");

                    Multipart multipart = new MimeMultipart();

                    BodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setContent(String.format("Hi %s %s. \n We are sorry to inform you that " +
                                    "your account has been suspended for <b>%s</b>.",
                            meeter.getMeeterName(), meeter.getMeeterSurname(), ban.get(Ban.BAN_DESCRIPTION)), "text/html");

                    multipart.addBodyPart(messageBodyPart);

                    message.setContent(multipart);

                    Transport.send(message);

                } catch (MessagingException | SQLException e) {
                    ResponseHelper.sendGenericError(out);
                }

                resp.sendRedirect(String.valueOf(req.getRequestURL()));
            }
        } catch (SQLException e) {
            resp.setContentType("application/json");
            ResponseHelper.sendGenericError(out);
        }
    }
}
