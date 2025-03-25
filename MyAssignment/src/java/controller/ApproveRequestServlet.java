/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author Admin
 */
@WebServlet("/approve_request")
public class ApproveRequestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User manager = (session != null) ? (User) session.getAttribute("user") : null;

        if (manager == null || !manager.getRole().getRname().equalsIgnoreCase("manage")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action"); // "approve" hoặc "reject"
        String abid = request.getParameter("abid");

        if (abid != null && ("approve".equals(action) || "reject".equals(action))) {
            AbsentRequestDBContext db = new AbsentRequestDBContext();
            AbsentRequest req = db.getRequestById(abid);

            if (req == null) {
                session.setAttribute("errorMessage", "Không tìm thấy đơn xin nghỉ!");
            } else if (req.getCreatedBy().equals(manager.getUsername())) {
                session.setAttribute("errorMessage", "Bạn không thể duyệt đơn của chính mình!");
            } else {
                int newStatus = action.equals("approve") ? 2 : 3;
                db.updateAbsentRequestStatus(abid, newStatus);
                session.setAttribute("successMessage", "Đã " + (newStatus == 2 ? "duyệt" : "từ chối") + " đơn thành công!");
            }
        } else {
            session.setAttribute("errorMessage", "Thao tác không hợp lệ!");
        }

        response.sendRedirect(request.getContextPath() + "/manage_dashboard");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
