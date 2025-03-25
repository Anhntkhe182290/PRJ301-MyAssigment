<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="data.User"%>
<%@page import="data.AbsentRequest"%>
<%@page import="java.util.List"%>
<%@page import="java.time.LocalDate"%>

<%
    User user = (User) session.getAttribute("user");
    List<AbsentRequest> requests = (List<AbsentRequest>) request.getAttribute("requests");
    String filterStatus = request.getParameter("status");
    String filterDate = request.getParameter("date");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Manage Dashboard</title>
        <link rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <h3>Xin chào, <%= user.getFullName() %> (Manage - <%= user.getDepartment().getDepName() %>)</h3>

            <% if (request.getAttribute("successMessage") != null) { %>
            <div class="alert alert-success"><%= request.getAttribute("successMessage") %></div>
            <% } %>
            <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-danger"><%= request.getAttribute("errorMessage") %></div>
            <% } %>

            <form class="form-inline" method="get" action="manage_dashboard">
                <div class="form-group">
                    <select name="status" class="form-control">
                        <option value="">-- Trạng thái --</option>
                        <option value="1" <%= "1".equals(filterStatus) ? "selected" : "" %>>In Process</option>
                        <option value="2" <%= "2".equals(filterStatus) ? "selected" : "" %>>Approved</option>
                        <option value="3" <%= "3".equals(filterStatus) ? "selected" : "" %>>Rejected</option>
                    </select>
                </div>
                <div class="form-group">
                    <input type="date" name="date" class="form-control" value="<%= filterDate != null ? filterDate : "" %>"/>
                </div>
                <button type="submit" class="btn btn-primary">Lọc</button>
                <a href="manage_profile" class="btn btn-default pull-right">My Profile</a>
            </form>

            <br/>
            <table class="table table-bordered table-hover">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tiêu đề</th>
                        <th>Nhân viên</th>
                        <th>Lý do</th>
                        <th>Từ ngày</th>
                        <th>Đến ngày</th>
                        <th>Ngày tạo</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                        <th>Agenda</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (requests != null && !requests.isEmpty()) {
                        for (AbsentRequest req : requests) {
                            String status = "Unknown";
                            switch (req.getStatus()) {
                                case 1: status = "In Process"; break;
                                case 2: status = "Approved"; break;
                                case 3: status = "Rejected"; break;
                            }
                    %>
                    <tr>
                        <td><%= req.getAbsenceId() %></td>
                        <td><%= req.getTitle() %></td>
                        <td><%= req.getCreatedBy() %></td>
                        <td><%= req.getReason() %></td>
                        <td><%= req.getFromDate() %></td>
                        <td><%= req.getToDate() %></td>
                        <td><%= req.getCreationDate() %></td>
                        <td>
                            <span class="label <%= req.getStatus() == 1 ? "label-warning" : (req.getStatus() == 2 ? "label-success" : "label-danger") %>">
                                <%= status %>
                            </span>
                        </td>
                        <td>
                            <% if (req.getStatus() == 1 && !req.getCreatedBy().equalsIgnoreCase(user.getUsername())) { %>
                            <form action="approve_request" method="post" style="display:inline;">
                                <input type="hidden" name="abid" value="<%= req.getAbsenceId() %>"/>
                                <input type="hidden" name="action" value="approve"/>
                                <button class="btn btn-xs btn-success" onclick="return confirm('Duyệt đơn này?')">Approve</button>
                            </form>
                            <form action="approve_request" method="post" style="display:inline;">
                                <input type="hidden" name="abid" value="<%= req.getAbsenceId() %>"/>
                                <input type="hidden" name="action" value="reject"/>
                                <button class="btn btn-xs btn-danger" onclick="return confirm('Từ chối đơn này?')">Reject</button>
                            </form>
                            <% } else { %>
                            <em><%= req.getCreatedBy().equalsIgnoreCase(user.getUsername()) ? "Không thể xử lý" : "Đã xử lý" %></em>
                            <% } %>
                        </td>
                        <td class="text-center">
                            <% if (req.getStatus() == 1) { %>
                            <button class="btn btn-xs btn-info" data-toggle="modal" data-target="#agendaModal_<%= req.getCreatedBy() %>">
                                See
                            </button>
                            <% } else { %>
                            <span class="text-muted">-</span>
                            <% } %>
                        </td>
                    </tr>
                    <% }} else { %>
                    <tr>
                        <td colspan="10" class="text-center">Không có đơn xin nghỉ nào.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <!-- Include Modal Agenda -->
        <jsp:include page="modal_agenda.jsp" />

        <!-- Scripts -->
        <script src="plugins/bootstrap/js/jquery.min.js"></script>
        <script src="plugins/bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
