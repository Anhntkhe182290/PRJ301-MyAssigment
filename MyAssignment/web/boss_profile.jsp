<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="data.User"%>
<%
    User user = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Boss Profile</title>
    <link rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css">
</head>
<body class="container mt-4">
    <h3>👤 Thông tin Boss - <%= user.getFullName() %></h3>

    <% if (request.getAttribute("successMessage") != null) { %>
        <div class="alert alert-success"><%= request.getAttribute("successMessage") %></div>
    <% } %>
    <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("errorMessage") %></div>
    <% } %>

    <form action="boss_profile" method="post">
        <div class="form-group">
            <label>Họ tên:</label>
            <input type="text" class="form-control" value="<%= user.getFullName() %>" readonly>
        </div>
        <div class="form-group">
            <label>Username:</label>
            <input type="text" class="form-control" value="<%= user.getUsername() %>" readonly>
        </div>
        <div class="form-group">
            <label>Ngày sinh:</label>
            <input type="date" class="form-control" value="<%= user.getDob() %>" readonly>
        </div>
        <div class="form-group">
            <label>Giới tính:</label>
            <input type="text" class="form-control" value="<%= user.getGender() == 1 ? "Nam" : "Nữ" %>" readonly>
        </div>
        <div class="form-group">
            <label>Chức vụ:</label>
            <input type="text" class="form-control" value="<%= user.getRole().getRname() %>" readonly>
        </div>
        <div class="form-group">
            <label>Phòng ban:</label>
            <input type="text" class="form-control" value="<%= user.getDepartment().getDepName() %>" readonly>
        </div>

        <hr>
        <h4>🔒 Đổi mật khẩu</h4>
        <div class="form-group">
            <label>Mật khẩu mới:</label>
            <input type="password" name="newPassword" class="form-control" required autocomplete="new-password">
        </div>
        <div class="form-group">
            <label>Xác nhận mật khẩu:</label>
            <input type="password" name="confirmPassword" class="form-control" required autocomplete="new-password">
        </div>

        <button type="submit" class="btn btn-primary">Cập nhật mật khẩu</button>
    </form>

    <br>
    <a href="boss_dashboard" class="btn btn-default">⬅ Quay lại trang chính</a>
</body>
</html>
