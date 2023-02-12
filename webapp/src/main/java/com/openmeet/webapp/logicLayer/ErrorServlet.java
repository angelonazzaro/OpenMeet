package com.openmeet.webapp.logicLayer;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * This class is used to handle 404 and 500 errors.
 *
 * @author Angelo Nazzaro
 */
public class ErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.processError(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.processError(req, resp);
    }

    /**
     * Handles GET and POST requests.
     *
     * @param req the request. It is used to get the error code.
     * @param resp the response. It is used to display the corresponding jsp file.
     *
     * @author Angelo Nazzaro
     */
    private void processError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorCode = (String) req.getAttribute("jakarta.servlet.error.status_code");

        // User is trying to access this page directly
        if (errorCode == null) {
            errorCode = "404";
        }

        req.setAttribute("title", "Error " + errorCode);

        req.getRequestDispatcher("WEB-INF/errors/" + errorCode + ".jsp").forward(req, resp);
    }
}
