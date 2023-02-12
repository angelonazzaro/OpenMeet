package com.openmeet.webapp.logicLayer;

import com.google.gson.Gson;
import com.openmeet.shared.helpers.ResponseHelper;
import com.openmeet.webapp.dataLayer.moderator.Moderator;
import com.openmeet.webapp.dataLayer.moderator.ModeratorDAO;
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

/**
 * Servlet that handles the login functionality.
 *
 * @author Angelo Nazzaro
 */
public class LoginServlet extends HttpServlet {

    /**
     * Gets the login page.
     *
     * @param req  The request object.
     * @param resp The response object.
     * @throws ServletException If an error occurs.
     * @throws IOException      If an error occurs.
     *
     * @author Angelo Nazzaro
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (isLogged(req)) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        req.setAttribute("view", "login");
        req.setAttribute("title", "Login");
        req.setAttribute("scripts", new String[]{"login"});

        req.getRequestDispatcher("WEB-INF/index.jsp").forward(req, resp);
    }

    /**
     * Handles the login functionality.
     *
     * @param req  The request object.
     * @param resp The response object.
     * @throws IOException If an error occurs.
     *
     * @author Angelo Nazzaro
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (isLogged(req)) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        PrintWriter out = resp.getWriter();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Check if all required parameters are initialized, if not send back an error message
        if (!ResponseHelper.checkStringFields(email, password)) {
            ResponseHelper.sendCustomError(out, "One or more required fields are missing.");
            return;
        }

        ModeratorDAO moderatorDAO = new ModeratorDAO((DataSource) getServletContext().getAttribute("DataSource"));
        // Check if user exists
        try {
            List<Moderator> moderators = moderatorDAO
                    .doRetrieveByCondition(
                            String.format(
                                    "%s = '%s' AND %s=SHA1('%s')",
                            Moderator.MODERATOR_EMAIL, email, Moderator.MODERATOR_PWD, password));

            if (moderators.isEmpty()) {
                ResponseHelper.sendCustomError(out, "The email or the password are incorrect. Please try again.");
                return;
            }

            // User has been found. Log in the user
            HttpSession session = req.getSession();
            session.setAttribute("user", moderators.get(0));

        } catch (SQLException e) {
            ResponseHelper.sendGenericError(out);
            return;
        }

        // Response
        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("redirectTo", req.getContextPath() + "/");
        ResponseHelper.sendGenericResponse(out, values);
    }

    /**
     * Checks if the user is logged in.
     *
     * @see com.openmeet.webapp.logicLayer.filters.AuthenticationFilter
     *
     * @param req The request object.
     * @return True if the user is logged in, false otherwise.
     *
     * @author Angelo
     */
    private boolean isLogged(HttpServletRequest req) {
        HttpSession currentUserSession = req.getSession(false);

        return currentUserSession != null && currentUserSession.getAttribute("user") != null;
    }
}
