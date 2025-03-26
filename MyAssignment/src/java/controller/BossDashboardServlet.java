/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.AbsentRequestDBContext;
import dal.DepartmentDBContext;
import data.AbsentRequest;
import data.Department;
import data.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet("/boss_dashboard")
public class BossDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !user.getRole().getRname().equalsIgnoreCase("boss")) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy filter
        String dep = request.getParameter("dep");
        String status = request.getParameter("status");
        String date = request.getParameter("date");

        AbsentRequestDBContext absentDB = new AbsentRequestDBContext();
        DepartmentDBContext depDB = new DepartmentDBContext();

        List<Department> departments = depDB.getAllDepartments();
        List<AbsentRequest> requests;

        if (dep != null && !dep.isEmpty() && !"all".equals(dep)) {
            int depId = Integer.parseInt(dep);
            requests = absentDB.getRequestsByDepartmentWithFilter(depId, status, date);
        } else {
            requests = absentDB.getAllRequestsWithFilter(status, date); // mới thêm
        }

        request.setAttribute("departments", departments);
        request.setAttribute("requests", requests);
        request.setAttribute("selectedDep", dep);
        request.setAttribute("selectedStatus", status);
        request.setAttribute("selectedDate", date);
        request.getRequestDispatcher("boss_dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !user.getRole().getRname().equalsIgnoreCase("boss")) {
            response.sendRedirect("login.jsp");
            return;
        }

        String abid = request.getParameter("abid");
        String action = request.getParameter("action");

        if (abid != null && action != null) {
            AbsentRequestDBContext db = new AbsentRequestDBContext();
            int newStatus = "approve".equals(action) ? 2 : 3;
            db.updateAbsentRequestStatus(abid, newStatus);
            session.setAttribute("successMessage", "Đã " + (newStatus == 2 ? "duyệt" : "từ chối") + " đơn thành công!");
        }

        response.sendRedirect("boss_dashboard");
    }

}
