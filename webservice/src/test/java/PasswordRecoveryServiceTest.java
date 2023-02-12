import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openmeet.webservice.services.PasswordRecoveryService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for PasswordRecoveryService
 *
 * @author Angelo Nazzaro
 */
public class PasswordRecoveryServiceTest {

    PasswordRecoveryService passwordRecoveryService;
    HttpServletRequest request;

    HttpServletResponse response;

    StringWriter stringWriter;

    PrintWriter writer;

    Gson gson;

    /**
     * Sets up the test environment.
     *
     * @throws IOException      If an error occurs.
     * @throws ServletException If an error occurs.
     *
     * @author Angelo Nazzaro
     */
    @BeforeEach
    public void setUp() throws IOException, ServletException {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://185.229.236.190:3306/OpenMeet");
        dataSource.setUsername("remote_usr");
        dataSource.setPassword("cicciobello123");

        passwordRecoveryService = new PasswordRecoveryService();
        ServletContext servletContext = mock(ServletContext.class);
        ServletConfig servletConfig = mock(ServletConfig.class);

        when(servletContext.getAttribute("DataSource")).thenReturn(dataSource);
        when(servletConfig.getServletContext()).thenReturn(servletContext);

        passwordRecoveryService.init(servletConfig);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);
    }

    /**
     * Tests the TC_3.1_1 Test case. It tests the presence of the email in the database.
     *
     * @author Angelo Nazzaro
     */
    @Test
    @Order(1)
    public void emailIsNotPresent() throws IOException {
        when(request.getParameter("action")).thenReturn("send-recovery-link");
        when(request.getParameter("email")).thenReturn("provaDoSave23@gmail.com");

        passwordRecoveryService.doPost(request, response);

        assertEquals("Email not found", stringWriter.toString());
    }

    /**
     * Tests the TC_3.1_2 Test case.
     *
     * @author Angelo Nazzaro
     */
    @Test
    @Order(2)
    public void passwordRecovered() throws IOException {
        when(request.getParameter("action")).thenReturn("send-recovery-link");
        when(request.getParameter("email")).thenReturn("provaDoSave@gmail.com");

        passwordRecoveryService.doPost(request, response);

        assertEquals("Link Sent", stringWriter.toString());
    }
}
