<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="data.User"%>
<%@page import="data.AbsentRequest"%>
<%@page import="java.util.List"%>

<%
    User user = (User) session.getAttribute("user");
    if (user == null || !user.getRole().getRname().equalsIgnoreCase("manage")) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<AbsentRequest> requests = (List<AbsentRequest>) 
            request.getAttribute("requests");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Manage Profile</title>
        <link rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css">
        <style>
            .container {
                margin-top: 20px;
            }
            .profile, .request-form {
                width: 48%;
                float: left;
                margin-right: 2%;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 10px;
                background: #f9f9f9;
            }
            .history {
                clear: both;
                margin-top: 40px;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 10px;
                background: #eef;
            }
            .message {
                color: green;
                font-weight: bold;
            }
            .error {
                color: red;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h3>Manage Profile - <%= user.getFullName() %></h3>

            <% if (request.getAttribute("successMessage") != null) { %>
            <p class="message"><%= request.getAttribute("successMessage") %></p>
            <% } %>
            <% if (request.getAttribute("errorMessage") != null) { %>
            <p class="error"><%= request.getAttribute("errorMessage") %></p>
            <% } %>

            <!-- Thông tin cá nhân & Đổi mật khẩu -->
            <div class="profile">
                <h4>Thông tin cá nhân</h4>
                <form action="manage_profile" method="post">
                    <input type="hidden" name="action" value="changePassword" />

                    <div class="form-group">
                        <label>Họ tên:</label>
                        <input type="text" class="form-control" value="<%= user.getFullName() %>" readonly />
                    </div>
                    <div class="form-group">
                        <label>Username:</label>
                        <input type="text" class="form-control" value="<%= user.getUsername() %>" readonly />
                    </div>
                    <div class="form-group">
                        <label>Ngày sinh:</label>
                        <input type="date" class="form-control" value="<%= user.getDob() %>" readonly />
                    </div>
                    <div class="form-group">
                        <label>Giới tính:</label>
                        <input type="text" class="form-control" value="<%= user.getGender() == 1 ? "Male" : "Female" %>" readonly />
                    </div>
                    <div class="form-group">
                        <label>Chức vụ:</label>
                        <input type="text" class="form-control" value="<%= user.getRole().getRname() %>" readonly />
                    </div>
                    <div class="form-group">
                        <label>Phòng ban:</label>
                        <input type="text" class="form-control" value="<%= user.getDepartment().getDepName() %>" readonly />
                    </div>

                    <h4>Đổi mật khẩu</h4>
                    <div class="form-group">
                        <label>Mật khẩu mới:</label>
                        <input type="password" name="newPassword" class="form-control" required />
                    </div>
                    <div class="form-group">
                        <label>Xác nhận mật khẩu:</label>
                        <input type="password" name="confirmPassword" class="form-control" required />
                    </div>
                    <button type="submit" class="btn btn-primary">Cập nhật mật khẩu</button>
                </form>
            </div>

            <!-- Gửi đơn xin nghỉ -->
            <div class="request-form">
                <h4>Gửi đơn xin nghỉ</h4>
                <form action="manage_profile" method="post">
                    <input type="hidden" name="action" value="request" />

                    <p><strong><%= user.getFullName() %> - Manage (<%= user.getDepartment().getDepName() %>)</strong></p>

                    <div class="form-group">
                        <label>Tiêu đề:</label>
                        <input type="text" name="title" class="form-control" required />
                    </div>
                    <div class="form-group">
                        <label>Từ ngày:</label>
                        <input type="date" name="fromDate" class="form-control" required />
                    </div>
                    <div class="form-group">
                        <label>Đến ngày:</label>
                        <input type="date" name="toDate" class="form-control" required />
                    </div>
                    <div class="form-group">
                        <label>Lý do:</label>
                        <textarea name="reason" class="form-control" required></textarea>
                    </div>
                    <button type="submit" class="btn btn-success">Gửi đơn</button>
                </form>
            </div>

            <!-- Lịch sử đơn nghỉ -->
            <div class="history">
                <h4>📌 Lịch sử đơn xin nghỉ</h4>
                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Tiêu đề</th>
                            <th>Lý do</th>
                            <th>Từ ngày</th>
                            <th>Đến ngày</th>
                            <th>Trạng thái</th>
                            <th>Ngày tạo</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (requests != null && !requests.isEmpty()) {
                            for (AbsentRequest req : requests) {
                                String statusText = "Unknown";
                                switch (req.getStatus()) {
                                    case 1: statusText = "In Process"; break;
                                    case 2: statusText = "Approved"; break;
                                    case 3: statusText = "Rejected"; break;
                                }
                        %>
                        <tr>
                            <td><%= req.getAbsenceId() %></td>
                            <td><%= req.getTitle() %></td>
                            <td><%= req.getReason() %></td>
                            <td><%= req.getFromDate() %></td>
                            <td><%= req.getToDate() %></td>
                            <td><%= statusText %></td>
                            <td><%= req.getCreationDate() %></td>
                            <td>
                                <% if (req.getStatus() == 1) { %>
                                <form action="edit_absent" method="get" style="display:inline;">
                                    <input type="hidden" name="absenceId" value="<%= req.getAbsenceId() %>">
                                    <button class="btn btn-xs btn-warning">Sửa</button>
                                </form>
                                <form action="manage_profile" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="abid" value="<%= req.getAbsenceId() %>">
                                    <button class="btn btn-xs btn-danger" onclick="return confirm('Xoá đơn này?')">Xoá</button>
                                </form>
                                <% } else { %>
                                <em>Đã xử lý</em>
                                <% } %>
                            </td>
                        </tr>
                        <% }} else { %>
                        <tr><td colspan="8" class="text-center">Không có đơn nào.</td></tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="plugins/bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
