package com.openmeet.webapp.logicLayer;

import com.google.gson.Gson;
import com.openmeet.shared.data.meeter.Meeter;
import com.openmeet.shared.data.report.Report;
import com.openmeet.shared.data.report.ReportDAO;
import com.openmeet.shared.utils.MultiMapList;
import com.openmeet.shared.utils.QueryJoinExecutor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

public class ArchivedReportsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("view", "archivedReports");
        req.setAttribute("title", "Archived Reports");
        req.setAttribute("heading", "Archived Reports");

        // Get Data
        QueryJoinExecutor qjx = new QueryJoinExecutor((DataSource) getServletContext().getAttribute("DataSource"));
        MultiMapList<String, String> data = new MultiMapList<>();

        try {
            /*
            SELECT Report.*, CONCAT(Meeter.meeterName, ' ', Meeter.meeterSurname) AS meeterfullName, Meeter.email
            FROM Report JOIN Meeter ON Meeter.id = Report.meeterReported
            WHERE Report.isArchived = TRUE
            */
            data = qjx.doRetrivedByJoin(
                    String.format(
                            "SELECT %s.*, CONCAT(%s, ' ', %s) AS meeterfullName, %s " +
                                    "FROM %s JOIN %s ON %s = %s " +
                                    "WHERE %s = TRUE",
                            Report.REPORT, Meeter.MEETER_MEETER_NAME, Meeter.MEETER_MEETER_SURNAME, Meeter.MEETER_EMAIL,
                            Report.REPORT, Meeter.MEETER, Meeter.MEETER_ID, Report.REPORT_MEETER_REPORTED,
                            Report.REPORT_IS_ARCHIVED));

        } catch (SQLException e) {
            resp.sendError(500, "Internal Server Error");
        }

        req.setAttribute("data", data);

        req.getRequestDispatcher("WEB-INF/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int reportId = Integer.parseInt(req.getParameter("reportId"));

        if (reportId <= 0) {
            resp.sendError(500, "Internal Server Error");
            return;
        }

        ReportDAO reportDAO = new ReportDAO((DataSource) getServletContext().getAttribute("DataSource"));
        HashMap<String, Boolean> values = new HashMap<>();
        values.put("isArchived", true);

        try {
            if (reportDAO.doUpdate(values, String.format("%s = %d", Report.REPORT_ID, reportId))) {
                resp.sendRedirect(req.getContextPath() + "/reports");
            }
        } catch (SQLException e) {
            resp.sendError(500, "Internal Server Error");
        }

    }
}
