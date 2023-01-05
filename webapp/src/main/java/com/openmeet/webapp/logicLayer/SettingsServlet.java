package com.openmeet.webapp.logicLayer;

import com.google.gson.Gson;
import com.openmeet.webapp.JSONResponse;
import com.openmeet.webapp.dataLayer.moderator.Moderator;
import com.openmeet.webapp.dataLayer.moderator.ModeratorDAO;
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

        JSONResponse jsonResponse = new JSONResponse();
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (name == null || surname == null || name.length() == 0 || surname.length() == 0) {
            jsonResponse.addPair("status", "error");
            jsonResponse.addPair("message", "One or more required fields are missing.");


            out.write(gson.toJson(jsonResponse.getResponse()));
            out.flush();
            return;
        }

        HttpSession session = req.getSession(false);
        Moderator user = (Moderator) session.getAttribute("user");

        user.setName(name);
        user.setSurname(surname);

        String password = req.getParameter("password");
        System.out.println("password" + password);
        // Regex check
        if (password != null && password.length() > 0) {

            if (!password.matches("(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}")) {
                jsonResponse.addPair("status", "error");
                jsonResponse.addPair("message", "The password does not meet the specified criteria.");

                out.write(gson.toJson(jsonResponse.getResponse()));
                out.flush();
                return;
            }

            user.setPwd(password);
            System.out.println("in");

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

                    jsonResponse.addPair("status", "error");
                    jsonResponse.addPair("message", "The file uploaded is not an image.");

                    out.write(gson.toJson(jsonResponse.getResponse()));
                    out.flush();
                    return;
                }

                // Checking that the file size does not exceed 1MB
                if (profilePicPart.getSize() > 1048576) {

                    jsonResponse.addPair("status", "error");
                    jsonResponse.addPair("message", "The file uploaded's size exceeds 1MB.");

                    out.write(gson.toJson(jsonResponse.getResponse()));
                    out.flush();
                    return;
                }

                String appPath = req.getServletContext().getRealPath("/");
                String basePath = appPath + "assets/uploads/moderators/" + user.getId();
                String uploadPath = basePath + "/profilePic." + fileExtension ;
                File userProfilePicFolder = new File(basePath);

                // If the folder does not exist, it means this is the first upload by the user
                // so the folder needs to bcreated
                if (!userProfilePicFolder.exists()) {

                    if (!userProfilePicFolder.mkdir()) {

                        jsonResponse.addPair("status", "error");
                        jsonResponse.addPair("message", "An error occurred while trying to upload your file. Try again later.");

                        out.write(gson.toJson(jsonResponse.getResponse()));
                        out.flush();
                        return;
                    }
                    // Save image for the first time
                    BufferedImage bufferedImage = ImageIO.read(profilePicPart.getInputStream());
                    ImageIO.write(bufferedImage, fileExtension, new File(uploadPath));
                    user.setProfilePic(uploadPath);
                }
            }

        } catch (ServletException e) {
            jsonResponse.addPair("status", "error");
            jsonResponse.addPair("message", "An error occurred, please try again later.");

            out.write(gson.toJson(jsonResponse.getResponse()));
            out.flush();
            return;
        }

        ModeratorDAO moderatorDAO = new ModeratorDAO((DataSource) getServletContext().getAttribute("DataSource"));

        try {
            if (moderatorDAO.doSaveOrUpdate(user)) {

                session.setAttribute("user", user);

                jsonResponse.addPair("status", "success");
                out.write(gson.toJson(jsonResponse.getResponse()));
                out.flush();
            }
        } catch (SQLException e) {
            jsonResponse.addPair("status", "error");
            jsonResponse.addPair("message", "An error occurred, please try again later.");

            out.write(gson.toJson(jsonResponse.getResponse()));
            out.flush();
        }
    }
}
