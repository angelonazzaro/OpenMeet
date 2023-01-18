package com.openmeet.webservice;

import com.openmeet.shared.data.meeter.Meeter;
import com.openmeet.shared.data.meeter.MeeterDAO;
import com.openmeet.shared.helpers.ResponseHelper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        logger.log(Level.INFO, "RegistrationServlet:doPost()");

        String email = request.getParameter("email");
        String pwd = request.getParameter("pwd");
        String meeterName = request.getParameter("meeterName");
        String meeterSurname = request.getParameter("meeterSurname");
        String birthDate = request.getParameter("birthDate");

        PrintWriter out = response.getWriter();

        //check if all the parameters are present
        if (!ResponseHelper.checkStringFields(new String[]{email, pwd})) {
            ResponseHelper.sendGenericError(out);
            logger.log(Level.SEVERE, "RegistrationServlet:doPost() - Error: missing parameters");
            return;
        }

        MeeterDAO meeterDAO = new MeeterDAO((DataSource) getServletContext().getAttribute("DataSource"));

        //check if the email is already in use

        try {
            List<Meeter> meeters = meeterDAO.doRetrieveByCondition(
                    String.format(
                            "%s.email = '%s' "
                            , Meeter.MEETER, email
                    )
            );

            if (!meeters.isEmpty()) {
                ResponseHelper.sendCustomError(out, "The email is already registered. Please try again.");
                return;
            }

        } catch (Exception e) {
            ResponseHelper.sendGenericError(out);
            logger.log(Level.SEVERE, "RegistrationServlet:doPost() - Error: " + e.getMessage());
            return;
        }
        //create the new meeter
        Meeter meeter = new Meeter();
        meeter.setEmail(email);
        meeter.setPwd(pwd);
        meeter.setMeeterName(meeterName);
        meeter.setMeeterSurname(meeterSurname);
        meeter.setBirthDate(Date.valueOf(birthDate));

        boolean success;

        try {
            success = meeterDAO.doSave(meeter.toHashMap
                    (Meeter.MEETER_EMAIL,
                            Meeter.MEETER_PWD,
                            Meeter.MEETER_MEETER_NAME,
                            Meeter.MEETER_MEETER_SURNAME,
                            Meeter.MEETER_BIRTH_DATE)
            );

        } catch (Exception e) {
            System.out.println(e.getMessage());
            ResponseHelper.sendGenericError(out);
            return;
        }

        if (success) {

            HashMap<String, String> values = new HashMap<>();
            values.put("status", "success");
            ResponseHelper.sendGenericResponse(out, values);
        } else {
            ResponseHelper.sendGenericError(out);
        }
    }
}