package com.openmeet.webapp.logicLayer.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * This class is used to filter all requests and check if the user is authenticated.
 *
 * @author Angelo Nazzaro
 * */
public class AuthenticationFilter implements Filter {

  private ServletContext context;

  public void init(FilterConfig fConfig) {
    this.context = fConfig.getServletContext();
    this.context.log("AuthenticationFilter initialized");
  }

  /**
   * Checks if the user is authenticated.
   * If not, the user will be redirected to the login page.
   *
   * @author Angelo Nazzaro
   * */
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;

    HttpSession currentUserSession = request.getSession(false);
    // We assume the user is logged
    String requestURI = request.getRequestURI();

    if (!requestURI.endsWith("logout") && !requestURI.endsWith("login") && !requestURI.contains("assets") && !requestURI.contains("error")) {
      // If the user is not logged, redirect to the login page
      if (currentUserSession == null || currentUserSession.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
      }
    }

    chain.doFilter(request, response); // pass the request along the filter chain
  }

  public void destroy() {
    this.context.log("AuthenticationFilter removed");
  }

}
