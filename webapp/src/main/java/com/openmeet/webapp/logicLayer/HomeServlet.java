package com.openmeet.webapp.logicLayer;

import com.google.gson.Gson;
import com.openmeet.shared.data.report.Report;
import com.openmeet.shared.data.report.ReportDAO;
import com.openmeet.shared.helpers.JSONResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("view", "dashboard");
        req.setAttribute("title", "Dashboard");

        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");
        ReportDAO reportDAO = new ReportDAO(dataSource);
        Gson gson = new Gson();

        try {
            // Get Report Ratio data (Archived vs Unarchived)
            List<Report> unarchivedReports = reportDAO.doRetrieveByCondition(String.format("%s.isArchived = false", Report.REPORT));
            List<Report> archivedReports = reportDAO.doRetrieveByCondition(String.format("%s.isArchived = true", Report.REPORT));

            // Calculate Percentages
            int totalReports = unarchivedReports.size() + archivedReports.size();
            float unarchivedReportsPcg = ((float) unarchivedReports.size() / totalReports) * 100;
            float archivedReportsPcg = ((float) archivedReports.size() / totalReports) * 100;

            JSONResponse reportsData = new JSONResponse();
            reportsData.addPair("unarchivedReportsPcg", String.valueOf(unarchivedReportsPcg));
            reportsData.addPair("archivedReportsPcg", String.valueOf(archivedReportsPcg));

            req.setAttribute("reportsData", gson.toJson(reportsData.getResponse()));

        } catch (SQLException e) {
            resp.sendError(500, "Internal Server Error");
        }


        req.getRequestDispatcher("WEB-INF/index.jsp").forward(req, resp);
    }
}
