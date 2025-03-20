<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="data.User" %>
<%@ page import="data.AbsentRequest" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff Dashboard</title>
        <style>
            .container {
                display: flex;
                justify-content: space-around;
                margin-top: 20px;
            }
            .profile, .request-form {
                width: 45%;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 10px;
                background: #f9f9f9;
            }
            .history {
                width: 90%;
                margin: 20px auto;
                border: 1px solid #ccc;
                border-radius: 10px;
                padding: 15px;
                background: #eef;
            }
            .history table {
                width: 100%;
                border-collapse: collapse;
            }
            .history th, .history td {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: left;
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
        <% 
            // 🛑 Kiểm tra session user
            User user = (User) session.getAttribute("user");
            if (user == null || user.getRole() == null || !user.getRole().getRname().equalsIgnoreCase("staff")) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            // Lấy danh sách đơn xin nghỉ của nhân viên hiện tại
            List<AbsentRequest> requests = (List<AbsentRequest>) request.getAttribute("requests");
        %>

        <h2>Welcome, <%= user.getFullName() %>!</h2>

        <%-- 🟢 Hiển thị thông báo cập nhật thành công/thất bại --%>
        <% if (session.getAttribute("successMessage") != null) { %>
        <p class="message"><%= session.getAttribute("successMessage") %></p>
        <% session.removeAttribute("successMessage"); %>
        <% } %>

        <% if (session.getAttribute("errorMessage") != null) { %>
        <p class="error"><%= session.getAttribute("errorMessage") %></p>
        <% session.removeAttribute("errorMessage"); %>
        <% } %>

        <div class="container">
            <!-- 🟢 Profile Section -->
            <div class="profile">
                <h3>My Profile</h3>
                <form action="staff_dashboard" method="post">
                    <label>Name:</label>
                    <input type="text" value="<%= user.getFullName() %>" readonly /><br>

                    <label>Username:</label>
                    <input type="text" value="<%= user.getUsername() %>" readonly /><br>

                    <label>Date of Birth:</label>
                    <input type="date" value="<%= user.getDob() %>" readonly /><br>

                    <label>Gender:</label>
                    <input type="text" value="<%= user.getGender() == 1 ? "Male" : "Female" %>" readonly /><br>

                    <label>Role:</label>
                    <input type="text" value="<%= user.getRole().getRname() %>" readonly /><br>

                    <label>Department:</label>
                    <input type="text" value="<%= user.getDepartment().getDepName() %>" readonly /><br>

                    <h4>Update Password</h4>
                    <label>New password:</label>
                    <input type="password" name="newPassword" required /><br>

                    <label>Confirm new password:</label>
                    <input type="password" name="confirmPassword" required /><br>

                    <button type="submit">Save changes</button>
                </form>
            </div>

            <!-- 🟢 Request Form Section -->
            <div class="request-form">
                <h3>Đơn xin nghỉ phép</h3>
                <form action="staff_dashboard" method="post">
                    <p>User: <%= user.getFullName() %>, Role: Staff, Dept: <%= user.getDepartment().getDepName() %></p>

                    <label>Tiêu đề:</label>
                    <input type="text" name="title" required /><br>

                    <label>Từ ngày:</label>
                    <input type="date" name="fromDate" required /><br>

                    <label>Đến ngày:</label>
                    <input type="date" name="toDate" required /><br>

                    <label>Lý do:</label>
                    <textarea name="reason" required></textarea><br>

                    <button type="submit">Gửi</button>
                </form>
            </div>
        </div>

        <!-- 🟢 Lịch sử đơn xin nghỉ -->
        <div class="history">
            <h3>📌 Lịch sử đơn xin nghỉ phép</h3>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Tiêu đề</th>
                    <th>Lý do</th>
                    <th>Từ ngày</th>
                    <th>Đến ngày</th>
                    <th>Trạng thái</th>
                </tr>
                <% if (requests != null && !requests.isEmpty()) { %>
                <% for (AbsentRequest requestObj : requests) { %>
                <tr>
                    <td><%= requestObj.getAbsenceId() %></td>
                    <td><%= requestObj.getTitle() %></td>
                    <td><%= requestObj.getReason() %></td>
                    <td><%= requestObj.getFromDate() %></td>
                    <td><%= requestObj.getToDate() %></td>
                    <td><%= requestObj.getStatus() == 1 ? "In Process" : (requestObj.getStatus() == 2 ? "Approved" : "Rejected") %></td>
                    <td><%= requestObj.getCreationDate() %></td>

                </tr>
                <% } %>
                <% } else { %>
                <tr><td colspan="6">Không có đơn xin nghỉ nào.</td></tr>
                <% } %>
            </table>
        </div>

        <!-- 🟢 Navigation Links -->
        <a href="logout">Logout</a> 
    </body>         
</html>
