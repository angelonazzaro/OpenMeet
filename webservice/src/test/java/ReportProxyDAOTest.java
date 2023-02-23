import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openmeet.shared.data.report.Report;
import com.openmeet.shared.data.report.ReportDAO;
import com.openmeet.webservice.exceptions.InvalidParameterException;
import com.openmeet.webservice.proxies.ReportProxyDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for ReportProxyDAO. It tests the doSave method.
 *
 * @author Francesco Granozio
 */
public class ReportProxyDAOTest {
    BasicDataSource dataSource;

    ReportProxyDAO reportProxyDAO;

    HttpServletRequest request;

    HttpServletResponse response;

    StringWriter stringWriter;

    PrintWriter writer;

    Gson gson;

    /**
     * Sets up the test environment.
     *
     * @throws IOException If an error occurs.
     *
     * @author Francesco Granozio
     */
    @BeforeEach
    public void setUp() throws IOException {
        dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://185.229.236.190:3306/OpenMeetTests");
        dataSource.setUsername("test_remote_usr");
        dataSource.setPassword("Testmargherita0!");

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        reportProxyDAO = new ReportProxyDAO(new ReportDAO(dataSource), request, writer);
    }

    /**
     * Clears the test environment.
     *
     * @throws SQLException If an error occurs.
     *
     * @author Francesco Granozio
     */
    @AfterEach
    public void clear() throws SQLException {
        when(request.getParameter("condition")).thenReturn(String.format("%s = 1", Report.REPORT_MEETER_REPORTED));
        reportProxyDAO.doDelete(null);
    }

    /**
     * Tests the TC_2.1_1 Test case. It tests the invalid description length.
     *
     * @author Francesco Granozio
     */
    @Test
    @Order(1)
    public void testInvalidReasonLength() {
        Report report = new Report();
        report.setMeeterReported(1);
        report.setMeeterReporter(2);
        report.setIsArchived(false);
        report.setCreationDate(new Timestamp(System.currentTimeMillis()));
        report.setReason("");

        when(request.getParameter("report")).thenReturn(gson.toJson(report));

        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> {
            reportProxyDAO.doSave(new Report());
        });

        assertEquals("Invalid parameter length - reason", exception.getMessage());
    }

    /**
     * Tests the TC_2.1_2 Test case.
     *
     * @author Francesco Granozio
     */
    @Test
    @Order(2)
    public void testValidReasonLength() throws SQLException {
        Report report = new Report();
        report.setMeeterReported(1);
        report.setMeeterReporter(2);
        report.setIsArchived(false);
        report.setCreationDate(new Timestamp(System.currentTimeMillis()));
        report.setReason("Spam");

        when(request.getParameter("report")).thenReturn(gson.toJson(report));

        assertTrue(reportProxyDAO.doSave(new Report()));
    }

    /**
     * Tests the TC_2.1_1 Test case. It tests the invalid description length with doSave hashmap variant.
     *
     * @author Francesco Granozio
     */
    @Test
    @Order(3)
    public void testInvalidReasonLengthHashMap() {
        HashMap<String, Object> values = new HashMap<>();
        values.put("meeterReported", "1");
        values.put("meeterReporter", "2");
        values.put("reason", "");
        values.put("isArchived", false);
        values.put("creationDate", new Timestamp(System.currentTimeMillis()).toString());

        when(request.getParameter("values")).thenReturn(gson.toJson(values));

        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> {
            reportProxyDAO.doSave(new HashMap<String, String>());
        });

        assertEquals("Invalid parameter length - reason", exception.getMessage());
    }

    /**
     * Tests the TC_2.1_2 Test case with doSave hashmap variant.
     *
     * @author Francesco Granozio
     */
    @Test
    @Order(3)
    public void testValidReasonLengthHashMap() throws SQLException {
        HashMap<String, Object> values = new HashMap<>();
        values.put("meeterReported", "1");
        values.put("meeterReporter", "2");
        values.put("reason", "Spam");
        values.put("isArchived", false);
        values.put("creationDate", new Timestamp(System.currentTimeMillis()).toString());

        when(request.getParameter("values")).thenReturn(gson.toJson(values));

        assertTrue(reportProxyDAO.doSave(new HashMap<String, String>()));
    }
}
