package controller;

import data.User;
import dal.UserDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user != null) {
            String role = user.getRole().getRname().toLowerCase().trim();
            System.out.println("DEBUG: Người dùng đã đăng nhập - Role: " + role);

            switch (role) {
                case "boss":
                    response.sendRedirect(request.getContextPath() + "/boss_dashboard.jsp");
                    return;
                case "manage":
                    response.sendRedirect(request.getContextPath() + "/manage_dashboard.jsp");
                    return;
                case "staff":
                    response.sendRedirect(request.getContextPath() + "/staff_dashboard.jsp"); // 🟢 Đã sửa đường dẫn
                    return;
                default:
                    session.invalidate();
                    request.setAttribute("errorMessage", "Tài khoản không có quyền hợp lệ!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
            }
        }

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDBContext userDB = new UserDBContext();
        User user = userDB.getUserByUsernameAndPassword(username, password);

        if (user != null) {
            System.out.println("DEBUG: Đăng nhập thành công - " + user.getUsername() + " (" + user.getRole().getRname() + ")");

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30 * 60);

            switch (user.getRole().getRname().toLowerCase()) {
                case "boss":
                    response.sendRedirect(request.getContextPath() + "/boss_dashboard.jsp");
                    return;
                case "manage":
                    response.sendRedirect(request.getContextPath() + "/manage_dashboard.jsp");
                    return;
                case "staff":
                    response.sendRedirect(request.getContextPath() + "/staff_dashboard.jsp");
                    return;
                default:
                    session.invalidate();
                    request.setAttribute("errorMessage", "Tài khoản không có quyền hợp lệ!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
