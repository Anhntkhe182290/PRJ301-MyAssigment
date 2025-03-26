<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="data.AbsentRequest"%>
<%@page import="data.User"%>
<%
    AbsentRequest requestObj = (AbsentRequest) request.getAttribute("absentRequest");
    User user = (User) session.getAttribute("user");

    boolean isEditable = requestObj != null &&
                         user != null &&
                         requestObj.getCreatedBy().equals(user.getUsername()) &&
                         requestObj.getStatus() == 1;
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Manage - Edit Absent Request</title>
        <link rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css">
        <style>
            .readonly {
                background-color: #f1f1f1;
            }
            .container {
                margin-top: 40px;
            }
        </style>
    </head>
    <body class="container">
        <h3>✏️ Quản lý - Sửa đơn nghỉ phép</h3>

        <% if (requestObj != null) { %>
        <form action="manage_edit_absent" method="post">
            <input type="hidden" name="absenceId" value="<%= requestObj.getAbsenceId() %>" />

            <div class="form-group">
                <label>Tiêu đề:</label>
                <input type="text" name="title" class="form-control"
                       value="<%= requestObj.getTitle() %>" <%= isEditable ? "" : "readonly class='readonly'" %> />
            </div>

            <div class="form-group">
                <label>Lý do:</label>
                <textarea name="reason" class="form-control" rows="3"
                          <%= isEditable ? "" : "readonly class='readonly'" %>><%= requestObj.getReason() %></textarea>
            </div>

            <div class="form-group">
                <label>Từ ngày:</label>
                <input type="date" name="fromDate" class="form-control"
                       value="<%= requestObj.getFromDate() %>" <%= isEditable ? "" : "readonly class='readonly'" %> />
            </div>

            <div class="form-group">
                <label>Đến ngày:</label>
                <input type="date" name="toDate" class="form-control"
                       value="<%= requestObj.getToDate() %>" <%= isEditable ? "" : "readonly class='readonly'" %> />
            </div>

            <div class="form-group">
                <label>Trạng thái:</label>
                <input type="text" class="form-control" readonly
                       value="<%= requestObj.getStatus() == 1 ? "In Process" : (requestObj.getStatus() == 2 ? "Approved" : "Rejected") %>" />
            </div>

            <% if (isEditable) { %>
            <button type="submit" name="action" value="update" class="btn btn-success">Cập nhật</button>
            <button type="submit" name="action" value="delete" class="btn btn-danger"
                    onclick="return confirm('Bạn có chắc muốn xoá đơn này?');">Xoá</button>
            <% } else { %>
            <p class="alert alert-warning">Bạn không thể chỉnh sửa đơn này vì đã được xử lý hoặc không phải do bạn tạo.</p>
            <% } %>
        </form>
        <% } else { %>
        <div class="alert alert-danger">Không tìm thấy đơn nghỉ phép.</div>
        <% } %>

        <a href="manage_profile" class="btn btn-default mt-3">⬅ Quay lại hồ sơ</a>
    </body>
</html>
