package com.openmeet.webapp.AndroidLogic;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class AndroidRequestDispatcher extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    String email = req.getParameter("email");
    String password = req.getParameter("password");
    PrintWriter out = resp.getWriter();

    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    System.out.println("request received");

    out.println("success" + email + " | " + password);

  }
}
