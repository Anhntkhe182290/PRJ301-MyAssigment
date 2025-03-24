package controller;

import dal.AbsentRequestDBContext;
import dal.UserDBContext;
import data.AbsentRequest;
import data.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/staff_dashboard")
public class StaffDashboardServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !user.getRole().getRname().equalsIgnoreCase("staff")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        AbsentRequestDBContext absentDB = new AbsentRequestDBContext();
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            String absenceId = request.getParameter("absenceId");
            absentDB.deleteById(absenceId);
            session.setAttribute("successMessage", "Xoá đơn nghỉ thành công!");
        } else if (request.getParameter("newPassword") != null) {
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            if (newPassword.equals(confirmPassword)) {
                new UserDBContext().updatePassword(user.getUsername(), newPassword);
                session.setAttribute("successMessage", "Mật khẩu đã được cập nhật thành công!");
            } else {
                session.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp!");
            }
        } else if (request.getParameter("title") != null) {
            AbsentRequest absentRequest = new AbsentRequest(
                    "AR" + System.currentTimeMillis(),
                    request.getParameter("title"),
                    request.getParameter("reason"),
                    request.getParameter("fromDate"),
                    request.getParameter("toDate"),
                    1, // in-process
                    user.getUsername(),
                    LocalDate.now().toString()
            );
            absentDB.insertAbsentRequest(absentRequest);
            session.setAttribute("successMessage", "Đơn xin nghỉ đã được gửi thành công!");
        }

        response.sendRedirect(request.getContextPath() + "/staff_dashboard");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !user.getRole().getRname().equalsIgnoreCase("staff")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        AbsentRequestDBContext absentDB = new AbsentRequestDBContext();
        List<AbsentRequest> requests = absentDB.getAbsentRequestsByUser(user.getUsername());

        // Truyền lại messages từ session -> request
        Object success = session.getAttribute("successMessage");
        Object error = session.getAttribute("errorMessage");
        if (success != null) {
            request.setAttribute("successMessage", success);
            session.removeAttribute("successMessage");
        }
        if (error != null) {
            request.setAttribute("errorMessage", error);
            session.removeAttribute("errorMessage");
        }

        request.setAttribute("user", user);
        request.setAttribute("requests", requests); // ⚠️ Đừng quên dòng này
        request.getRequestDispatcher("/staff_dashboard.jsp").forward(request, response);
    }
}
