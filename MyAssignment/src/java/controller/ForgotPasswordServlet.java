/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.UserDBContext;
import data.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
import java.util.UUID;
import javax.mail.*;
import javax.mail.internet.*;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        UserDBContext userDB = new UserDBContext();
        User user = userDB.getByEmail(email);

        if (user == null) {
            request.setAttribute("errorMessage", "Email không tồn tại!");
            request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
            return;
        }

        String token = UUID.randomUUID().toString();
        userDB.saveResetToken(email, token);

        sendResetEmail(email, token);
        request.setAttribute("successMessage", "Vui lòng kiểm tra email để đặt lại mật khẩu.");
        request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
    }

    private void sendResetEmail(String email, String token) {
        String host = "smtp.gmail.com";
        String from = "your-email@gmail.com";
        String password = "your-email-password";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Đặt lại mật khẩu");
            message.setText("Nhấn vào đây để đặt lại mật khẩu: http://localhost:8080/reset-password?token=" + token);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
