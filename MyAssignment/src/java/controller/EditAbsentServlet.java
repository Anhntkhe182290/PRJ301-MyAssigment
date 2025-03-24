package controller;

import dal.AbsentRequestDBContext;
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

@WebServlet("/edit_absent")
public class EditAbsentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String absenceId = request.getParameter("absenceId");
        if (absenceId == null) {
            response.sendRedirect("staff_dashboard");
            return;
        }

        AbsentRequestDBContext absentDB = new AbsentRequestDBContext();
        AbsentRequest absentRequest = absentDB.getAbsentRequestById(absenceId);

        if (absentRequest == null) {
            response.sendRedirect("staff_dashboard");
            return;
        }

        request.setAttribute("absentRequest", absentRequest);
        request.getRequestDispatcher("edit_absent.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String absenceId = request.getParameter("absenceId");

        AbsentRequestDBContext absentDB = new AbsentRequestDBContext();

        if ("delete".equals(action)) {
            absentDB.deleteById(absenceId);
            session.setAttribute("successMessage", "Đơn nghỉ đã được xoá thành công!");
            response.sendRedirect("staff_dashboard");
            return;
        }

        if ("update".equals(action)) {
            String title = request.getParameter("title");
            String reason = request.getParameter("reason");
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");

            AbsentRequest updatedRequest = new AbsentRequest(
                absenceId,
                title,
                reason,
                fromDate,
                toDate,
                1,
                user.getUsername(),
                LocalDate.now().toString()
            );

            absentDB.updateAbsentRequestContent(updatedRequest);
            session.setAttribute("successMessage", "Đơn nghỉ đã được cập nhật thành công!");
            response.sendRedirect("staff_dashboard");
        }
    }
} 
