package controller;

import dal.AbsentRequestDBContext;
import data.AbsentRequest;
import data.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/manage_edit_absent")
public class ManageEditAbsentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String absenceId = request.getParameter("absenceId");
        if (absenceId == null) {
            response.sendRedirect("manage_profile");
            return;
        }

        AbsentRequestDBContext db = new AbsentRequestDBContext();
        AbsentRequest absentRequest = db.getAbsentRequestById(absenceId);

        if (absentRequest == null) {
            response.sendRedirect("manage_profile");
            return;
        }

        request.setAttribute("absentRequest", absentRequest);
        request.getRequestDispatcher("manage_edit_absent.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String absenceId = request.getParameter("absenceId");

        AbsentRequestDBContext db = new AbsentRequestDBContext();
        AbsentRequest existing = db.getAbsentRequestById(absenceId);

        if (existing == null || existing.getStatus() != 1 || !existing.getCreatedBy().equals(user.getUsername())) {
            session.setAttribute("errorMessage", "Bạn không được phép sửa đơn này!");
            response.sendRedirect("manage_profile");
            return;
        }

        if ("delete".equals(action)) {
            db.deleteById(absenceId);
            session.setAttribute("successMessage", "Đơn nghỉ đã được xoá thành công!");
            response.sendRedirect("manage_profile");
            return;
        }

        if ("update".equals(action)) {
            String title = request.getParameter("title");
            String reason = request.getParameter("reason");
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");

            AbsentRequest updatedRequest = new AbsentRequest(
                    absenceId, title, reason, fromDate, toDate,
                    1, user.getUsername(), existing.getCreationDate()
            );

            db.updateAbsentRequestContent(updatedRequest);
            session.setAttribute("successMessage", "Đơn nghỉ đã được cập nhật thành công!");
            response.sendRedirect("manage_profile");
        }
    }
}
