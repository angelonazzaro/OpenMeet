package com.openmeet.webservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.openmeet.shared.data.meeter.Meeter;
import com.openmeet.shared.data.meeter.MeeterDAO;

import com.openmeet.shared.helpers.ResponseHelper;
import jakarta.servlet.http.*;

import javax.sql.DataSource;


public class LoginServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        logger.log(Level.INFO, "LoginServlet:doPost()");

        String email = request.getParameter("email");
        String pwd = request.getParameter("pwd");

        PrintWriter out = response.getWriter();

        if (!ResponseHelper.checkStringFields(new String[]{email, pwd})) {
            ResponseHelper.sendGenericError(out);
            logger.log(Level.SEVERE, "LoginServlet:doPost() - Error: missing parameters");
            return;
        }

        MeeterDAO meeterDAO = new MeeterDAO((DataSource) getServletContext().getAttribute("DataSource"));
        // Check if user exists

        try {
            List<Meeter> meeters = meeterDAO.doRetrieveByCondition(
                    String.format(
                            "%s.email = '%s' " +
                                    "AND " +
                                    "%s.pwd=SHA1('%s')"
                            , Meeter.MEETER, email, Meeter.MEETER, pwd
                    )
            );

            if (meeters.isEmpty()) {
                ResponseHelper.sendCustomError(out, "The email or the password are incorrect. Please try again.");
                return;
            }

        }
        catch (Exception e) {
            ResponseHelper.sendGenericError(out);
            logger.log(Level.SEVERE, "LoginServlet:doPost() - Error: " + e.getMessage());
            return;
        }

        // Response
        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        ResponseHelper.sendGenericResponse(out, values);

    }
}