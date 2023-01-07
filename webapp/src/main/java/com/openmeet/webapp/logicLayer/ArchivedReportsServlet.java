package com.openmeet.webapp.logicLayer;

import com.openmeet.shared.data.meeter.Meeter;
import com.openmeet.shared.data.report.Report;
import com.openmeet.shared.utils.MultiMapList;
import com.openmeet.shared.utils.QueryJoinExecutor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

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
            data = qjx.doRetrivedByJoin(String.format("SELECT %s.*, CONCAT(%s.meeterName, ' ', %s.meeterSurname) AS meeterfullName, %s.email FROM %s JOIN %s ON %s.id = %s.meeterReported WHERE %s.status = 1",
                    Report.REPORT,  Meeter.MEETER, Meeter.MEETER, Meeter.MEETER, Report.REPORT,  Meeter.MEETER,  Meeter.MEETER, Report.REPORT, Report.REPORT));

        } catch (SQLException ignored) {}

        req.setAttribute("data", data);

        req.getRequestDispatcher("WEB-INF/index.jsp").forward(req, resp);
    }
}
