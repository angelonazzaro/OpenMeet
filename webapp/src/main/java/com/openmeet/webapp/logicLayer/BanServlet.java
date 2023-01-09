package com.openmeet.webapp.logicLayer;

import com.openmeet.shared.data.ban.Ban;
import com.openmeet.shared.data.ban.BanDAO;
import com.openmeet.shared.data.meeter.Meeter;
import com.openmeet.shared.data.report.Report;
import com.openmeet.shared.utils.MultiMapList;
import com.openmeet.shared.utils.QueryJoinExecutor;
import com.openmeet.webapp.dataLayer.moderator.Moderator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

public class BanServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setAttribute("view", "bans");
    req.setAttribute("title", "Bans");
    req.setAttribute("heading", "Bans");

    // Get Data
    QueryJoinExecutor qjx = new QueryJoinExecutor((DataSource) getServletContext().getAttribute("DataSource"));
    MultiMapList<String, String> data = new MultiMapList<>();

    try {
      data = qjx.doRetrivedByJoin(String.format("SELECT %s.*, CONCAT(%s.meeterName, ' ', %s.meeterSurname) AS meeterfullName, %s.email FROM %s JOIN %s ON %s.id = %s.meeterReported WHERE %s.status = 1", Report.REPORT, Meeter.MEETER, Meeter.MEETER, Meeter.MEETER, Report.REPORT, Meeter.MEETER, Meeter.MEETER, Report.REPORT, Report.REPORT));

    } catch (SQLException ignored) {
    }

    req.setAttribute("data", data);

    req.getRequestDispatcher("WEB-INF/index.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String description = req.getParameter("description");
    String strEndTime = req.getParameter("endTime");
    int meeterId = Integer.parseInt(req.getParameter("meeterId"));

    Timestamp endTime = null;

    if (strEndTime != null && strEndTime.length() > 0) {
      endTime = Timestamp.valueOf(strEndTime);
    }

    BanDAO banDAO = new BanDAO((DataSource) getServletContext().getAttribute("DataSource"));
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
      throw new RuntimeException(e);
    }

  }
}
