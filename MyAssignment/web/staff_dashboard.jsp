<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="data.User" %>
<%@ page import="data.AbsentRequest" %>
<%@ page import="java.util.List" %>

<%
    List<AbsentRequest> requests = (List<AbsentRequest>) request.getAttribute("requests");
%>

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
            User user = (User) session.getAttribute("user");
            if (user == null || user.getRole() == null || !user.getRole().getRname().equalsIgnoreCase("staff")) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }
        %>

        <h2>Welcome, <%= user.getFullName() %>!</h2>

        <% if (request.getAttribute("successMessage") != null) { %>
        <p class="message"><%= request.getAttribute("successMessage") %></p>
        <% } %>

        <% if (request.getAttribute("errorMessage") != null) { %>
        <p class="error"><%= request.getAttribute("errorMessage") %></p>
        <% } %>

        <div class="container">
            <!-- Profile -->
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

            <!-- Request Form -->
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
                    <th>Ngày tạo</th>
                    <th>Thao tác</th>
                </tr>

                <% 
                    if (requests != null && !requests.isEmpty()) {
                        for (AbsentRequest req : requests) { 
                %>
                <tr>
                    <td><%= req.getAbsenceId() %></td>
                    <td><%= req.getTitle() %></td>
                    <td><%= req.getReason() %></td>
                    <td><%= req.getFromDate() %></td>
                    <td><%= req.getToDate() %></td>
                    <td>
                        <%
                            String statusText = "Unknown";
                            switch (req.getStatus()) {
                                case 1: statusText = "In Process"; break;
                                case 2: statusText = "Approved"; break;
                                case 3: statusText = "Rejected"; break;
                            }
                        %>
                        <%= statusText %>
                    </td>
                    <td><%= req.getCreationDate() %></td>
                    <td>
                        <% if (req.getStatus() == 1) { %>
                        <form action="edit_absent" method="get" style="display:inline;">
                            <input type="hidden" name="absenceId" value="<%= req.getAbsenceId() %>"/>
                            <button type="submit">Sửa</button>
                        </form>

                        <form action="staff_dashboard" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="delete"/>
                            <input type="hidden" name="absenceId" value="<%= req.getAbsenceId() %>"/>
                            <button type="submit" onclick="return confirm('Bạn có chắc muốn xoá đơn này?')">Xoá</button>
                        </form>
                        <% } else { %>
                        Đã xử lý
                        <% } %>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr><td colspan="8">Không có đơn xin nghỉ nào.</td></tr>
                <% } %>
            </table>
        </div>

        <div style="text-align: right; margin-bottom: 10px;">
            <form action="logout" method="post" style="display:inline;">
                <button class="btn btn-danger">Đăng xuất</button>
            </form>
        </div>

    </body>
</html>