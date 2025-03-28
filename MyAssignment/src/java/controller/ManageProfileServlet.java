package controller;

import dal.AbsentRequestDBContext;
import dal.UserDBContext;
import data.AbsentRequest;
import data.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/manage_profile")
public class ManageProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null || !user.getRole().getRname().equalsIgnoreCase("manage")) {
            response.sendRedirect("login.jsp");
            return;
        }

        AbsentRequestDBContext db = new AbsentRequestDBContext();
        List<AbsentRequest> requests = db.getAbsentRequestsByUser(user.getUsername());

        request.setAttribute("user", user);
        request.setAttribute("requests", requests);
        request.getRequestDispatcher("manage_profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !"manage".equalsIgnoreCase(user.getRole().getRname())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        if ("changePassword".equals(action)) {
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp.");
            } else {
                UserDBContext userDB = new UserDBContext();
                userDB.updatePassword(user.getUsername(), newPassword);
                request.setAttribute("successMessage", "Cập nhật mật khẩu thành công!");
            }

        } else if ("request".equals(action)) {
            String title = request.getParameter("title");
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            String reason = request.getParameter("reason");

            if (title != null && fromDate != null && toDate != null && reason != null) {
                String abid = UUID.randomUUID().toString().substring(0, 8);
                String createdAt = java.time.LocalDate.now().toString();
                int status = 1; // In process

                AbsentRequest requestModel = new AbsentRequest(
                        abid,
                        title,
                        reason,
                        fromDate,
                        toDate,
                        status,
                        user.getUsername(),
                        createdAt
                );

                AbsentRequestDBContext db = new AbsentRequestDBContext();
                db.insert(requestModel);
                request.setAttribute("successMessage", "Đã gửi đơn xin nghỉ thành công.");
            } else {
                request.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin đơn nghỉ.");
            }

        } else if ("delete".equals(action)) {
            String abid = request.getParameter("abid");
            if (abid != null && !abid.isEmpty()) {
                AbsentRequestDBContext db = new AbsentRequestDBContext();
                db.deleteById(abid);
                request.setAttribute("successMessage", "Đã xoá đơn nghỉ thành công.");
            }
        }

        // Load lại danh sách
        AbsentRequestDBContext absentDB = new AbsentRequestDBContext();
        List<AbsentRequest> requests = absentDB.getAbsentRequestsByUser(user.getUsername());

        request.setAttribute("user", user);
        request.setAttribute("requests", requests);

        request.getRequestDispatcher("/manage_profile.jsp").forward(request, response);
    }

}
