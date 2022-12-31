package com.openmeet.webapp.logicLayer;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (isLogged(req)) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (isLogged(req)) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();
        Map<String, String> response = new HashMap();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Check if all required parameters are initialized, if not send back an error message
        if (email == null || password == null || email.length() == 0 || password.length() == 0) {

            response.put("status", "error");
            response.put("message", "One or more required fields are missing.");

            // Serialization: json -> {"status: "error", "message": "One or more required fields are missing."}
            out.write(gson.toJson(response));
            out.flush();
            return;
        }

        // DAO logic here

        // Response
        response.put("status", "success");
        response.put("redirectTo", req.getContextPath() + "/");
        out.write(gson.toJson(response));
        out.flush();
    }

    private boolean isLogged(HttpServletRequest req) {
        HttpSession currentUserSession = req.getSession(false);

        return currentUserSession != null && currentUserSession.getAttribute("user") != null;
    }
}
