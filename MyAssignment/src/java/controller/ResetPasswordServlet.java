package controller;

import dal.UserDBContext;
import data.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");

        if (token == null || token.isEmpty()) {
            request.setAttribute("errorMessage", "Token không hợp lệ!");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        UserDBContext userDB = new UserDBContext();
        User user = userDB.getUserByToken(token);

        if (user == null) {
            request.setAttribute("errorMessage", "Token không tồn tại hoặc đã hết hạn!");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("token", token);
        request.getRequestDispatcher("reset_password.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("password");

        UserDBContext userDB = new UserDBContext();
        User user = userDB.getUserByToken(token);

        if (user == null) {
            request.setAttribute("errorMessage", "Token không hợp lệ!");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("successMessage", "Mật khẩu đã được đặt lại thành công! Vui lòng đăng nhập.");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
