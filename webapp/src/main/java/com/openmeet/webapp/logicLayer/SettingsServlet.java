package com.openmeet.webapp.logicLayer;

import com.google.gson.Gson;
import com.openmeet.webapp.dataLayer.moderator.Moderator;
import com.openmeet.webapp.dataLayer.moderator.ModeratorDAO;
import com.openmeet.webapp.helpers.ResponseHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;

import javax.imageio.ImageIO;
import javax.sql.DataSource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024,      // 1 MB
        maxRequestSize = 1024 * 1024 * 5   // 5 MB
)
public class SettingsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("view", "settings");
        req.setAttribute("title", "Settings");
        req.setAttribute("scripts", new String[]{"settings"});

        req.getRequestDispatcher("WEB-INF/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");

        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (ResponseHelper.checkStringFields(new String[] {name, surname})) {
            ResponseHelper.sendCustomError(out, gson, "One or more required fields are missing.");
            return;
        }

        HttpSession session = req.getSession(false);
        Moderator user = (Moderator) session.getAttribute("user");
        HashMap<String, String> valuesToUpdate = new HashMap<>();

        user.setName(name);
        user.setSurname(surname);

        valuesToUpdate.put("name", name);
        valuesToUpdate.put("surname", surname);

        String password = req.getParameter("password");
        // Regex check
        if (password != null && password.length() > 0) {

            if (!password.matches("(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}")) {
                ResponseHelper.sendCustomError(out, gson, "The password does not meet the specified criteria.");
                return;
            }

            user.setPwd(password);
            valuesToUpdate.put("name", name);
        }

        // File Upload if any
        try {
            Part profilePicPart = req.getPart("profilePic");

            if (profilePicPart != null && profilePicPart.getSize() > 0) {

                String fileName = profilePicPart.getSubmittedFileName();
                String[] splittedFileName = fileName.split("\\.");
                String fileExtension = splittedFileName[splittedFileName.length - 1];

                // Check if the file uploaded is an img
                if (!fileExtension.equals("jpeg") && !fileExtension.equals("jpg") && !fileExtension.equals("png")) {
                    ResponseHelper.sendCustomError(out, gson, "The file uploaded is not an image.");
                    return;
                }

                // Checking that the file size does not exceed 1MB
                if (profilePicPart.getSize() > 1048576) {
                    ResponseHelper.sendCustomError(out, gson, "The file uploaded's size exceeds 1MB.");
                    return;
                }

                String appPath = req.getServletContext().getRealPath("/");
                String basePath = appPath + "assets/uploads/moderators/" + user.getId();
                String uploadPath = basePath + "/profilePic." + fileExtension ;
                File userProfilePicFolder = new File(basePath);

                // If the folder does not exist, it means this is the first upload by the user
                // so the folder needs to be created
                if (!userProfilePicFolder.exists()) {

                    if (!userProfilePicFolder.mkdir()) {
                        ResponseHelper.sendCustomError(out, gson, "An error occurred while trying to upload your file. Try again later.");
                        return;
                    }
                    // Save image for the first time
                    BufferedImage bufferedImage = ImageIO.read(profilePicPart.getInputStream());
                    ImageIO.write(bufferedImage, fileExtension, new File(uploadPath));
                    user.setProfilePic(uploadPath);
                    valuesToUpdate.put("profilePic", uploadPath);
                }
            }

        } catch (ServletException e) {
            ResponseHelper.sendGenericError(out, gson);
            return;
        }

        ModeratorDAO moderatorDAO = new ModeratorDAO((DataSource) getServletContext().getAttribute("DataSource"));

        try {
            if (moderatorDAO.doUpdate(valuesToUpdate, Moderator.MODERATOR + ".id=" + user.getId())) {

                session.setAttribute("user", user);
                HashMap<String, String> values = new HashMap<>();
                values.put("status", "success");

                ResponseHelper.sendGenericResponse(out, gson, values);
            }
        } catch (SQLException e) {
            ResponseHelper.sendGenericError(out, gson);
        }
    }
}
