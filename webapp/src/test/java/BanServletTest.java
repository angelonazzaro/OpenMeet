import com.google.gson.Gson;
import com.openmeet.shared.data.ban.BanDAO;
import com.openmeet.shared.helpers.JSONResponse;
import com.openmeet.webapp.dataLayer.moderator.Moderator;
import com.openmeet.webapp.logicLayer.BanServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 */
public final class BanServletTest {

    static Tomcat tomcat;

    HttpURLConnection connection;
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

    @BeforeEach
    public void setUpServletContext() throws LifecycleException, IOException {
        tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.start();

        Context context = tomcat.addContext("/", System.getProperty("java.io.tmpdir"));
        Tomcat.addServlet(context, "BanServlet", "com.openmeet.webapp.logicLayer.BanServlet");
        context.addServletMappingDecoded("/ban", "BanServlet");

        URL url = new URL("http://localhost:8080/Gradle___com_openmeet___openmeet_webapp_1_0_SNAPSHOT_war__exploded_/ban");
        connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
    }

    @BeforeEach
    public void setUp() {
        banServlet = new BanServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        banDAO = mock(BanDAO.class);
        moderator = mock(Moderator.class);
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        jsonResponse = new JSONResponse();
        gson = new Gson();
    }

    @BeforeEach
    public void authenticate() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(moderator);
    }

    @AfterAll
    public static void tearDown() throws LifecycleException {
        tomcat.stop();
    }

    @Test
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

    @Test
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

    @Test
    public void testValidDescriptionLength() throws IOException {

        String requestBody = "meeterId=1&endTime=&description=Too many reports for spam";
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();

        assertTrue(connection.getHeaderField("Content-type").contains("text/html"));
        assertEquals(200, connection.getResponseCode());
    }

    @Test
    public void testInvalidEndTime() throws IOException {
        String requestBody = "meeterId=1&endTime=2022-11-24T13:02&description=Too many reports for spam";
        connection.setRequestProperty("Content-type", "application/json");

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();

        assertTrue(connection.getHeaderField("Content-type").contains("text/html"));
        assertEquals(200, connection.getResponseCode());
    }

    @Test
    public void testCorrectBan() throws IOException {
        String requestBody = "meeterId=1&endTime=2024-06-02T12:39&description=Too many reports for spam";
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();

        assertTrue(connection.getHeaderField("Content-type").contains("text/html"));
        assertEquals(200, connection.getResponseCode());
    }

}
