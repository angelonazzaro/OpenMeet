package com.openmeet.webapp.logicLayer;

import com.google.gson.Gson;
import com.openmeet.webapp.dataLayer.moderator.Moderator;
import com.openmeet.webapp.dataLayer.moderator.ModeratorDAO;
import com.openmeet.webapp.helpers.ResponseHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (isLogged(req)) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        req.setAttribute("view", "login");
        req.setAttribute("title", "Login");
        req.setAttribute("scripts", new String[]{"login.js"});

        req.getRequestDispatcher("WEB-INF/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (isLogged(req)) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Check if all required parameters are initialized, if not send back an error message
        if (email == null || password == null || email.length() == 0 || password.length() == 0) {
            ResponseHelper.sendCustomError(out, gson, "One or more required fields are missing.");
            return;
        }

        ModeratorDAO moderatorDAO = new ModeratorDAO((DataSource) getServletContext().getAttribute("DataSource"));
        // Check if user exists
        try {
            List<Moderator> moderators = moderatorDAO
                    .doRetrieveByCondition(String.format("%s.email = '%s' AND %s.pwd=SHA1('%s')",
                            Moderator.MODERATOR, email, Moderator.MODERATOR, password));

            if (moderators.isEmpty()) {
                ResponseHelper.sendCustomError(out, gson, "The email or the password are incorrect. Please try again.");
                return;
            }

            // User has been found. Log in the user
            HttpSession session = req.getSession();
            session.setAttribute("user", moderators.get(0));

        } catch (SQLException e) {
            ResponseHelper.sendGenericError(out, gson);
            return;
        }

        // Response
        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("redirectTo", req.getContextPath() + "/");
        ResponseHelper.sendGenericResponse(out, gson, values);
    }

    private boolean isLogged(HttpServletRequest req) {
        HttpSession currentUserSession = req.getSession(false);

        return currentUserSession != null && currentUserSession.getAttribute("user") != null;
    }
}
