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
import java.util.List;

@WebServlet("/manage_dashboard")
public class ManagerDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User manager = (session != null) ? (User) session.getAttribute("user") : null;

        if (manager == null || manager.getRole() == null
                || !manager.getRole().getRname().equalsIgnoreCase("manage")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Xử lý thông báo
        Object success = session.getAttribute("successMessage");
        if (success != null) {
            request.setAttribute("successMessage", success.toString());
            session.removeAttribute("successMessage");
        }
        
        

        // Lấy filter nếu có
        String statusParam = request.getParameter("status");
        String dateParam = request.getParameter("date");

        AbsentRequestDBContext absentDB = new AbsentRequestDBContext();
        List<AbsentRequest> requests;

        if ((statusParam != null && !statusParam.isEmpty()) || (dateParam != null && !dateParam.isEmpty())) {
            Integer status = (statusParam != null && !statusParam.isEmpty()) ? Integer.parseInt(statusParam) : null;
            requests = absentDB.filterRequestsByStatusAndDate(manager.getDepartment().getDepid(), status, dateParam);
        } else {
            requests = absentDB.getRequestsByDepartment(manager.getDepartment().getDepid());
        }

        request.setAttribute("user", manager);
        request.setAttribute("requests", requests);
        request.getRequestDispatcher("/manage_dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
}
