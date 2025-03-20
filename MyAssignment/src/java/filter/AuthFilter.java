package filter;

import data.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*_dashboard"}) // ✅ Lọc tất cả dashboard
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        String uri = req.getRequestURI();

        // 🛑 Nếu chưa đăng nhập, chuyển hướng về trang login
        if (user == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // 🟢 Lấy quyền hạn từ user
        String role = user.getRole().getRname().toLowerCase().trim();

        // 🛑 Kiểm tra quyền hạn trước khi vào dashboard
        if ((uri.contains("boss_dashboard") && !role.equals("boss")) ||
            (uri.contains("manage_dashboard") && !role.equals("manage")) || // ✅ Đúng theo database
            (uri.contains("staff_dashboard") && !role.equals("staff"))) {
            res.sendRedirect(req.getContextPath() + "/login.jsp"); // 🛑 Không đủ quyền → Đá về login
            return;
        }

        chain.doFilter(request, response); // 🟢 Nếu hợp lệ, tiếp tục vào dashboard
    }
}
