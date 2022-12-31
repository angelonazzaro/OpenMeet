package com.openmeet.webapp.logicLayer;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LogoutServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession currentUserSession = req.getSession(false);

        // Destroy current user session if not null
        if (currentUserSession != null && currentUserSession.getAttribute("user") != null)
            currentUserSession.invalidate();

        // Redirect to login page
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
