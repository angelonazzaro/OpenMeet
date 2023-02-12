package com.openmeet.webapp.logicLayer;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet that handles the logout functionality.
 *
 * @author Angelo Nazzaro
 */
public class LogoutServlet extends HttpServlet {

    /**
     * Handles the logout functionality.
     *
     * @param req  The request object.
     * @param resp The response object.
     * @throws IOException If an error occurs.
     *
     * @author Angelo Nazzaro
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession currentUserSession = req.getSession(false);

        // Destroy current user session if not null
        if (currentUserSession != null && currentUserSession.getAttribute("user") != null)
            currentUserSession.invalidate();

        // Redirect to login page
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
