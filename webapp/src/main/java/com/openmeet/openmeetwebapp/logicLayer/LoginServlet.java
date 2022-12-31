package com.openmeet.openmeetwebapp.logicLayer;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

        req.getRequestDispatcher("WEB-INF/index.jsp").forward(req, resp);
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

        System.out.println(email == null);

        // Check if all required parameters are initialized, if not send back an error message
        if (email == null || password == null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            Map<String, String> response = new HashMap();
            response.put("type", "error");
            response.put("msg", "One or more required fields are missing.");

            // Serialization: json -> {"type: "error", "msg": "One or more required fields are missing."}
            out.write(gson.toJson(response));
            out.flush();
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/");
    }

    private boolean isLogged(HttpServletRequest req) {
        return req.getSession(false) != null;
    }
}
