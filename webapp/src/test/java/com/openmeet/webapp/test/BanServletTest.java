package com.openmeet.webapp.test;

import com.google.gson.Gson;
import com.openmeet.shared.data.ban.Ban;
import com.openmeet.shared.data.ban.BanDAO;
import com.openmeet.shared.helpers.JSONResponse;
import com.openmeet.webapp.dataLayer.moderator.Moderator;
import com.openmeet.webapp.logicLayer.BanServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for BanServlet. It tests the doPost method.
 *
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 */
public final class BanServletTest {

     BasicDataSource dataSource;
     BanServlet banServlet;
     HttpServletRequest request;
     HttpServletResponse response;
     HttpSession session;
     BanDAO banDAO;
     Moderator moderator;
     StringWriter stringWriter;
     PrintWriter writer;
     JSONResponse jsonResponse;
     Gson gson;

     /**
      * Sets up the test environment.
      *
      * @throws ServletException If an error occurs.
      *
      * @author Angelo Nazzaro
      * @author Francesco Granozio
      */
    @BeforeEach
    public void setUp() throws ServletException {
        dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://185.229.236.190:3306/OpenMeet");
        dataSource.setUsername("remote_usr");
        dataSource.setPassword("db_password");

        banServlet = new BanServlet();
        ServletContext servletContext = mock(ServletContext.class);
        ServletConfig servletConfig = mock(ServletConfig.class);

        when(servletContext.getAttribute("DataSource")).thenReturn(dataSource);
        when(servletConfig.getServletContext()).thenReturn(servletContext);

        banServlet.init(servletConfig);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        banDAO = mock(BanDAO.class);
        moderator = new Moderator();

        moderator.setEmail("prova@email.com");
        moderator.setId(1);

        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        jsonResponse = new JSONResponse();
        gson = new Gson();
    }

    /**
     * Authenticates the user.
     *
     * @author Angelo Nazzaro
     */
    @BeforeEach
    public void authenticate() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(moderator);
    }

    /**
     * Clears the database.
     *
     * @throws SQLException If an error occurs.
     *
     * @author Angelo Nazzaro
     */
    @AfterEach
    public void clear() throws SQLException {
        banDAO = new BanDAO(dataSource);
        banDAO.doDelete(String.format("%s = 1", Ban.BAN_MEETER_ID));
    }

    /**
     * Tests the TC_1.1_1 Test case. It tests the invalid description length.
     *
     * @throws IOException If an error occurs.
     *
     * @author Angelo Nazzaro
     */
    @Test @Order(1)
    public void testInvalidDescriptionLengthInferior() throws IOException {
        when(request.getParameter("meeterId")).thenReturn("1");
        when(request.getParameter("endTime")).thenReturn(null);
        when(request.getParameter("description")).thenReturn("");

        when(response.getWriter()).thenReturn(writer);

        banServlet.doPost(request, response);
        writer.flush();

        jsonResponse.addPair("status", "error");
        jsonResponse.addPair("message", "An error occurred, please try again later.");

        assertEquals(gson.toJson(jsonResponse.getResponse()), stringWriter.toString());
    }

    /**
     * Tests the TC_1.1_1 Test case. It tests the invalid description length.
     *
     * @throws IOException If an error occurs.
     *
     * @author Angelo Nazzaro
     */
    @Test @Order(2)
    public void testInvalidDescriptionLengthSuperior() throws IOException {
        when(request.getParameter("meeterId")).thenReturn("1");
        when(request.getParameter("endTime")).thenReturn(null);
        when(request.getParameter("description")).thenReturn("Welcome to the Random Phrase and " +
                "Idiom Generator. There will be times when you may need more than a random word for what you want to " +
                "accomplish, and this free online tool can help. The use of this tool is quite simple. " +
                "All you need to do is indicate the number of random phrases you'd like to be displayed and then hit " +
                "the \"Generate Random Phrases\" button. Once done, your chosen number of idioms will be displayed " +
                "along with the meaning of the idiom");

        when(response.getWriter()).thenReturn(writer);

        banServlet.doPost(request, response);
        writer.flush();

        jsonResponse.addPair("status", "error");
        jsonResponse.addPair("message", "An error occurred, please try again later.");

        assertEquals(gson.toJson(jsonResponse.getResponse()), stringWriter.toString());
    }


    /**
     * Tests the TC_1.2_1 Test case. It tests the invalid end time.
     *
     * @throws IOException If an error occurs.
     *
     * @author Angelo Nazzaro
     */
    @Test @Order(3)
    public void testInvalidEndTime() throws IOException {
        when(request.getParameter("meeterId")).thenReturn("1");
        when(request.getParameter("endTime")).thenReturn("2022-11-24T13:02");
        when(request.getParameter("description")).thenReturn("Too many reports for spam");

        when(response.getWriter()).thenReturn(writer);

        banServlet.doPost(request, response);
        writer.flush();

        jsonResponse.addPair("status", "error");
        jsonResponse.addPair("message", "An error occurred, please try again later.");

        assertEquals(gson.toJson(jsonResponse.getResponse()), stringWriter.toString());
    }

    /**
     * Tests the TC_1.2_2 Test case.
     *
     * @throws IOException If an error occurs.
     *
     * @author Angelo Nazzaro
     */
    @Test @Order(4)
    public void testCorrectBan() throws IOException {
        when(request.getParameter("meeterId")).thenReturn("1");
        when(request.getParameter("endTime")).thenReturn("2024-11-24T13:02");
        when(request.getParameter("description")).thenReturn("Too many reports for spam");

        when(response.getWriter()).thenReturn(writer);

        banServlet.doPost(request, response);
        writer.flush();

        jsonResponse.addPair("status", "error");
        jsonResponse.addPair("message", "An error occurred, please try again later.");

        assertEquals("Ban Saved", stringWriter.toString());
    }

}
