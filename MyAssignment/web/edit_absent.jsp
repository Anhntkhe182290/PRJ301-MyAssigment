<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="data.AbsentRequest"%>
<%@page import="data.User"%>
<%
    AbsentRequest requestObj = (AbsentRequest) request.getAttribute("absentRequest");
    User user = (User) session.getAttribute("user");

    boolean isEditable = requestObj != null &&
                         user != null &&
                         requestObj.getCreatedBy().equals(user.getUsername()) &&
                         requestObj.getStatus() == 1; // 1 = in-process
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Sửa Đơn Nghỉ Phép</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                padding: 20px;
            }
            input, textarea {
                width: 100%;
                padding: 8px;
                margin: 5px 0;
            }
            .readonly {
                background-color: #eee;
            }
        </style>
    </head>
    <body>
        <h2>Chi tiết Đơn Nghỉ Phép</h2>

        <form method="post" action="<%= request.getContextPath() %>/edit_absent">
            <input type="hidden" name="absenceId" value="<%= requestObj.getAbsenceId() %>" />

            <label>Tiêu đề:</label>
            <input type="text" name="title" value="<%= requestObj.getTitle() %>" <%= isEditable ? "" : "readonly class='readonly'" %> />

            <label>Lý do:</label>
            <textarea name="reason" rows="4" <%= isEditable ? "" : "readonly class='readonly'" %>><%= requestObj.getReason() %></textarea>

            <label>Ngày bắt đầu:</label>
            <input type="date" name="fromDate" value="<%= requestObj.getFromDate() %>" <%= isEditable ? "" : "readonly class='readonly'" %> />

            <label>Ngày kết thúc:</label>
            <input type="date" name="toDate" value="<%= requestObj.getToDate() %>" <%= isEditable ? "" : "readonly class='readonly'" %> />

            <label>Trạng thái:</label>
            <%
        String statusText = "";
        switch (requestObj.getStatus()) {
            case 1:
                statusText = "Đang chờ duyệt (in-process)";
                break;
            case 2:
                statusText = "Đã duyệt";
                break;
            case 3:
                statusText = "Bị từ chối";
                break;
            default:
                statusText = "Không xác định";
        }
            %>

            <input type="text" value="<%= statusText %>" readonly class="readonly" />

            <br/><br/>

            <% if (isEditable) { %>
            <button type="submit" name="action" value="update">Cập nhật</button>
            <button type="submit" name="action" value="delete" onclick="return confirm('Bạn có chắc muốn xoá đơn này?');">Xoá</button>
            <% } else { %>
            <p><i>Bạn không có quyền chỉnh sửa hoặc xoá đơn này.</i></p>
            <% } %>
        </form>

        <br/>
        <%-- Quay lại dashboard đúng theo role --%>
        <a href="<%= request.getContextPath() %>/<%= user.getRole().getRname().equalsIgnoreCase("staff") ? "staff_dashboard" : "manage_dashboard" %>">
            ⬅ Quay lại Dashboard
        </a>
    </body>
</html>
