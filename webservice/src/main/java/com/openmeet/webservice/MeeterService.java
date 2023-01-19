package com.openmeet.webservice;

import com.openmeet.shared.data.meeter.Meeter;
import com.openmeet.shared.data.meeter.MeeterDAO;
import com.openmeet.shared.helpers.ResponseHelper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class MeeterService extends HttpServlet {

    public static final String DO_RETRIEVE_BY_CONDITION = "doRetrieveByCondition";

    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String operation = request.getParameter("operation");

        if (operation.equals(DO_RETRIEVE_BY_CONDITION)){

            System.out.println("Operazione di doRetrieveByCondition" + operation);

            PrintWriter out = response.getWriter();
            String condition = request.getParameter("condition");

            System.out.println("Condizione di doRetrieveByCondition" + condition);

            List<Meeter> meeters = null;

            MeeterDAO meeterDAO = new MeeterDAO((DataSource) getServletContext().getAttribute("DataSource"));
            try {
                meeters = meeterDAO.doRetrieveByCondition(condition);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Query eseguita");

            HashMap<String, String> values = new HashMap<>();
            values.put("status", "success");
            ResponseHelper.sendGenericResponse(out, values);
        }
    }
}
