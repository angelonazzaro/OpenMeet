package com.openmeet.webapp.logicLayer;

import com.openmeet.shared.data.ban.Ban;
import com.openmeet.shared.data.ban.BanDAO;
import com.openmeet.shared.data.meeter.Meeter;
import com.openmeet.shared.helpers.ResponseHelper;
import com.openmeet.shared.utils.MultiMapList;
import com.openmeet.shared.utils.QueryJoinExecutor;
import com.openmeet.webapp.dataLayer.moderator.Moderator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class BanServlet extends HttpServlet {
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String description = req.getParameter("description");
        String strEndTime = req.getParameter("endTime");
        int meeterId = Integer.parseInt(req.getParameter("meeterId"));

        Timestamp endTime = null;

        if (strEndTime != null && strEndTime.length() > 0) {
            endTime = Timestamp.valueOf(strEndTime);
        }

        PrintWriter out = resp.getWriter();

        BanDAO banDAO = new BanDAO((DataSource) getServletContext().getAttribute("DataSource"));

        // if banId is set, modify the ban associated
        if (req.getParameter("banId") != null) {
            int banId = Integer.parseInt((String) req.getParameter("banId"));

            try {
                List<Ban> bans = banDAO.doRetrieveByCondition(String.format("%s = %d", Ban.BAN_ID, banId));

                if (!bans.isEmpty()) {
                    Ban ban = bans.get(0);

                    if (endTime != null && ban.getStartTime().after(endTime)) {
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
                ResponseHelper.sendGenericError(out);
                return;
            }

        }

        // Check if the metter already has a ban in the same period or has been permanently banned
        try {
            List<Ban> meeterBans = banDAO.doRetrieveByCondition(String.format("%s = %d AND (%s IS NULL)",
                    Ban.BAN_MEETER_ID, meeterId, Ban.BAN_END_TIME));

            // user is already banned
            if (!meeterBans.isEmpty()) {
                HashMap<String, String> values = new HashMap<>();

                values.put("status", "error");
                values.put("msg", "The meeter is already banned!");

                ResponseHelper.sendGenericResponse(out, values);
                return;
            }

        } catch (SQLException e) {
            ResponseHelper.sendGenericError(out);
            return;
        }

        Moderator user = (Moderator) req.getSession(false).getAttribute("user");
        Ban ban = new Ban();

        ban.setModeratorId(user.getId());
        ban.setDescription(description);
        ban.setMeeterId(meeterId);
        ban.setStartTime(new Timestamp(System.currentTimeMillis()));

        if (endTime != null) ban.setEndTime(endTime);

        try {
            if (banDAO.doSave(ban)) {
                resp.sendRedirect(String.valueOf(req.getRequestURL()));
            }
        } catch (SQLException e) {
            ResponseHelper.sendGenericError(out);
        }
    }
}
