package com.openmeet.webservice.services;

import com.openmeet.shared.data.meeter.Meeter;
import com.openmeet.shared.data.meeter.MeeterDAO;
import com.openmeet.shared.helpers.ResponseHelper;
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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet that handles password recovery activity.
 *
 * @author Sl1mSha4dey
 */
public class PasswordRecoveryService extends HttpServlet {

    private static final HashMap<String, String> tokens = new HashMap<>();
    private static final String from = "staff.openmeet@gmail.com";
    private static final Logger logger = Logger.getLogger(PasswordRecoveryService.class.getName());

    /**
     * @param request  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String token = request.getParameter("pwdTkn");

        if (token == null || token.length() < 32 || token.length() > 64 || !tokens.containsKey(token)) {
            response.sendError(404, "Page Not Found");
            return;
        }

        MeeterDAO meeterDAO = new MeeterDAO((DataSource) getServletContext().getAttribute("DataSource"));
        String receiverEmail = tokens.get(token);

        try {
            List<Meeter> meeterList = meeterDAO.doRetrieveByCondition(String.format("%s = '%s'", Meeter.MEETER_EMAIL, receiverEmail));

            if (meeterList.isEmpty()) {
                logger.log(Level.INFO, "PasswordRecoveryService:doPost() - INFO: No Meeter with email '" + receiverEmail + "' found");
                return;
            }

            Meeter meeter = meeterList.get(0);

            String newPassword = this.generatePasswordOrToken(8, 16, false);

            meeter.setPwd(newPassword);

            HashMap<String, String> values = new HashMap<>();
            values.put("pwd", meeter.getPwd());

            if (!meeterDAO.doUpdate(values, String.format("%s = %d", Meeter.MEETER_ID, meeter.getId()))) {
                response.sendError(505, "Internal Server Error");
                return;
            }

            this.sendEmail(tokens.get(token), "OpenMeet | Your password has been reset",
                    String.format("Hi %s %s. This is your new password: <b>%s</b>", meeter.getMeeterName(),
                            meeter.getMeeterSurname(), newPassword));

            tokens.remove(token);

            request.getRequestDispatcher("WEB-INF/passwordRecovered.jsp").forward(request, response);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "PasswordRecoveryService:doPost() - Error: " + e.getMessage());
            response.sendError(505, "Internal Server Error");
        }

    }

    /**
     * @param request  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String receiverEmail = request.getParameter("email");
        String action = request.getParameter("action");

        if (!ResponseHelper.checkStringFields(receiverEmail, action)) return;

        MeeterDAO meeterDAO = new MeeterDAO((DataSource) getServletContext().getAttribute("DataSource"));
        PrintWriter out = response.getWriter();

        try {
            List<Meeter> meeterList = meeterDAO.doRetrieveByCondition(String.format("%s = '%s'", Meeter.MEETER_EMAIL, receiverEmail));

            if (meeterList.isEmpty()) {
                out.write("Email not found");
                logger.log(Level.INFO, "PasswordRecoveryService:doPost() - INFO: No Meeter with email '" + receiverEmail + "' found");
                return;
            }

            Meeter meeter = meeterList.get(0);

            if (!action.equals("send-recovery-link")) {
                return;
            }

            String tokenToRemove = null;

            for (Map.Entry<String, String> entry : tokens.entrySet()) {
                if (entry.getValue().equals(receiverEmail)) {
                    tokenToRemove = entry.getKey();
                }
            }

            if (tokenToRemove != null) {
                tokens.remove(tokenToRemove);
            }

            String token;

            do{
                token = this.generatePasswordOrToken(32, 64, true);
            } while (tokens.containsKey(token));

            tokens.put(token, meeter.getEmail());

            String link = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
                    + "/PasswordRecoveryService?pwdTkn=" + token;

            this.sendEmail(meeter.getEmail(), "OpenMeet | Password Recovery", String.format("Hi %s %s.<br>You have requested a new password. " +
                            "Follow this link to reset your password: <a named=\"%s\" id=\"%s\" href=\"%s\">here</a>.<br>" +
                            "If you did not request a new password, ignore this email.", meeter.getMeeterName(), meeter.getMeeterSurname()
                    , token, token, link));

            out.write("Link Sent");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "PasswordRecoveryService:doPost() - Error: " + e.getMessage());
        }

    }

    /**
     * @param to is email address of the Meeter who requested the activity
     * @param subject of email to send
     * @param body of email to send
     */
    private void sendEmail(String to, String subject, String body) {
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
            MimeMessage message = new MimeMessage(session);

            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html");

            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException mex) {
            logger.log(Level.SEVERE, "PasswordRecoveryService:doPost() - Error: " + mex.getMessage());
        }
    }

    //CRIMINALE
    private String generatePasswordOrToken(int minLength, int maxLength, boolean isToken) {
        // password only
        String characters = "0123456789abcdefghijklmnopqrstuvwxyz!@#$%^&*()ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        if (isToken) {
            characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@$~-_"; /*&'-._~:/?#@!$()*+,;=*/
        }

        StringBuilder token = new StringBuilder();
        Random random = new Random();
        int tokenLength = random.nextInt(minLength, maxLength);

        for (int i = 0; i < tokenLength; i++) {
            token.append(characters.charAt(random.nextInt(characters.length())));
        }

        return token.toString();
    }
}
