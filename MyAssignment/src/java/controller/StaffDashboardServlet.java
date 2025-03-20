package controller;

import dal.AbsentRequestDBContext;
import dal.UserDBContext;
import data.AbsentRequest;
import data.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/staff_dashboard")
public class StaffDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 🛑 Kiểm tra xem nhân viên đã đăng nhập chưa
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || user.getRole() == null || !user.getRole().getRname().equalsIgnoreCase("staff")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 🟢 Lấy danh sách đơn xin nghỉ của nhân viên hiện tại
        AbsentRequestDBContext absentDB = new AbsentRequestDBContext();
        List<AbsentRequest> requests = absentDB.getAbsentRequestsByUser(user.getUsername());

        // 🟢 Gửi danh sách đơn và thông tin User đến JSP
        request.setAttribute("requests", requests);
        request.setAttribute("user", user);

        request.getRequestDispatcher("/staff_dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || user.getRole() == null || !user.getRole().getRname().equalsIgnoreCase("staff")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        boolean hasError = false;

        // 🟢 Xử lý cập nhật mật khẩu
        if (request.getParameter("newPassword") != null) {
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            if (newPassword.equals(confirmPassword)) {
                UserDBContext userDB = new UserDBContext();
                userDB.updatePassword(user.getUsername(), newPassword);
                session.setAttribute("successMessage", "Mật khẩu đã được cập nhật thành công!");
            } else {
                session.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp!");
                hasError = true;
            }
        }

        // 🟢 Xử lý gửi đơn xin nghỉ
        if (request.getParameter("title") != null && request.getParameter("fromDate") != null && request.getParameter("toDate") != null) {
            String title = request.getParameter("title");
            String reason = request.getParameter("reason");
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");

            AbsentRequest absentRequest = new AbsentRequest(
                "AR" + System.currentTimeMillis(),
                title,
                reason,
                fromDate,
                toDate,
                1, // 1 = 'in-process'
                user.getUsername(),
                LocalDate.now().toString()
            );

            AbsentRequestDBContext absentDB = new AbsentRequestDBContext();
            absentDB.insertAbsentRequest(absentRequest);
            session.setAttribute("successMessage", "Đơn xin nghỉ đã được gửi thành công!");
        }

        response.sendRedirect(request.getContextPath() + "/staff_dashboard");
    }
}
