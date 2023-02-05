package com.openmeet.webservice.services;

import com.openmeet.shared.data.meeter.Meeter;
import com.openmeet.shared.data.meeter.MeeterDAO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordRecoveryService extends HttpServlet {

    private static final String senderEmail = "staff.openmeet@gmail.com";
    private static final Logger logger = Logger.getLogger(PasswordRecoveryService.class.getName());

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        String receiverEmail = request.getParameter("email");

        if (receiverEmail == null || receiverEmail.length() == 0) return;

        MeeterDAO meeterDAO = new MeeterDAO((DataSource) getServletContext().getAttribute("DataSource"));

        try {
            List<Meeter> meeterList = meeterDAO.doRetrieveByCondition(String.format("%s = '%s'", Meeter.MEETER_EMAIL, receiverEmail));

            if (meeterList.isEmpty()) {
                logger.log(Level.INFO, "PasswordRecoveryService:doPost() - INFO: No Meeter with email '" + receiverEmail + "' found");
                return;
            }

            Meeter meeter = meeterList.get(0);
            String newPassword = this.generatePassword();

            meeter.setPwd(newPassword);

            HashMap<String, String> values = new HashMap<>();
            values.put("pwd", meeter.getPwd());
            if (!meeterDAO.doUpdate(values, String.format("%s = %d", Meeter.MEETER_ID, meeter.getId()))) {
                return;
            }

            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(
                    properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("staff.openmeet@gmail.com", "tohybegjuqpwbzji");
                        }
                    });

            try {
                MimeMessage message = new MimeMessage(session);

                message.setFrom(new InternetAddress(senderEmail));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
                message.setSubject("OpenMeet | Password Recovery");
                message.setText(String.format("Hi %s %s. This is your new password: %s", meeter.getMeeterName(),
                        meeter.getMeeterSurname(), newPassword));

                Transport.send(message);

            } catch (MessagingException mex) {
                logger.log(Level.SEVERE, "PasswordRecoveryService:doPost() - Error: " + mex.getMessage());
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "PasswordRecoveryService:doPost() - Error: " + e.getMessage());
        }

    }

    private String generatePassword() {
        String characters = "0123456789abcdefghijklmnopqrstuvwxyz!@#$%^&*()ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder password = new StringBuilder();
        int minLength = 8;
        int maxLength = 17; // 17 because bound in nextInt is exclusive
        Random random = new Random();
        int passwordLength = random.nextInt(minLength, maxLength);

        for (int i = 0; i < passwordLength; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }

        return password.toString();
    }
}
