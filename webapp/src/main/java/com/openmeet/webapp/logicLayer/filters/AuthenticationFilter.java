package com.openmeet.webapp.logicLayer.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) {
        this.context = fConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession currentUserSession = request.getSession(false);
        // We assume the user is logged
        String requestURI = request.getRequestURI();

        if (!requestURI.endsWith("logout") && !requestURI.endsWith("login") && !requestURI.contains("assets")) {
            // If the user is not logged, redirect to the login page
            if (currentUserSession == null || currentUserSession.getAttribute("user") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
        }

        chain.doFilter(request, response); // pass the request along the filter chain
    }

    public void destroy() {
        // we can close resources here
        this.context.log("AuthenticationFilter removed");
    }
    
}
