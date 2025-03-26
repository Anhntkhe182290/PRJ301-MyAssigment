package controller;

import dal.AbsentRequestDBContext;
import dal.UserDBContext;
import data.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/boss_profile")
public class BossProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !"boss".equalsIgnoreCase(user.getRole().getRname())) {
            response.sendRedirect("login.jsp");
            return;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("boss_profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !"boss".equalsIgnoreCase(user.getRole().getRname())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp.");
        } else {
            UserDBContext db = new UserDBContext();
            db.updatePassword(user.getUsername(), newPassword);
            request.setAttribute("successMessage", "Đã cập nhật mật khẩu thành công.");
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("boss_profile.jsp").forward(request, response);
    }
}
