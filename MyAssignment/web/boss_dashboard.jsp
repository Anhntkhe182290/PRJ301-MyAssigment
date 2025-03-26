<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="data.AbsentRequest"%>
<%@page import="data.Department"%>
<%@page import="data.User"%>
<%@page import="java.util.List"%>
<%
    User user = (User) session.getAttribute("user");
    List<Department> departments = (List<Department>) request.getAttribute("departments");
    List<AbsentRequest> requests = (List<AbsentRequest>) request.getAttribute("requests");
    String selectedDep = (String) request.getAttribute("selectedDep");
    String selectedStatus = (String) request.getAttribute("selectedStatus");
    String selectedDate = (String) request.getAttribute("selectedDate");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Boss Dashboard</title>
        <link rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css"/>
        <style>
            .sidebar {
                float: left;
                width: 20%;
            }
            .content {
                float: right;
                width: 78%;
            }
            .btn-active {
                font-weight: bold;
                border: 2px solid red;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid mt-3">
            <div class="row">
                <!-- Left: Filter by department -->
                <div class="col-md-3 sidebar">
                    <h5>Departments List</h5>
                    <form method="get" action="boss_dashboard">
                        <input type="hidden" name="status" value="<%= selectedStatus != null ? selectedStatus : "" %>" />
                        <input type="hidden" name="date" value="<%= selectedDate != null ? selectedDate : "" %>" />
                        <button class="btn btn-block <%= (selectedDep == null || "all".equals(selectedDep)) ? "btn-active btn-secondary" : "btn-outline-secondary" %>" name="dep" value="all">All Departments</button>
                        <% for (Department d : departments) { %>
                        <button class="btn btn-block <%= String.valueOf(d.getDepid()).equals(selectedDep) ? "btn-active btn-secondary" : "btn-outline-secondary" %>" name="dep" value="<%= d.getDepid() %>"><%= d.getDepName() %></button>
                        <% } %>
                    </form>
                    <br/>
                    <a href="boss_profile" class="btn btn-danger btn-block">My Profile</a>
                    <a href="agenda.jsp" class="btn btn-info btn-block mt-2">📅 View Agenda</a>
                </div>

                <!-- Right: Request table -->
                <div class="col-md-9 content">
                    <h3>Leave Application List</h3>

                    <% if (session.getAttribute("successMessage") != null) { %>
                    <div class="alert alert-success"><%= session.getAttribute("successMessage") %></div>
                    <% session.removeAttribute("successMessage"); } %>

                    <form class="form-inline mb-3" method="get" action="boss_dashboard">
                        <input type="hidden" name="dep" value="<%= selectedDep != null ? selectedDep : "" %>" />
                        <div class="form-group me-2">
                            <label>Status:</label>
                            <select name="status" class="form-control">
                                <option value="">All</option>
                                <option value="1" <%= "1".equals(selectedStatus) ? "selected" : "" %>>In Process</option>
                                <option value="2" <%= "2".equals(selectedStatus) ? "selected" : "" %>>Approved</option>
                                <option value="3" <%= "3".equals(selectedStatus) ? "selected" : "" %>>Rejected</option>
                            </select>
                        </div>
                        <div class="form-group me-2">
                            <label>Date:</label>
                            <input type="date" name="date" class="form-control" value="<%= selectedDate != null ? selectedDate : "" %>"/>
                        </div>
                        <button type="submit" class="btn btn-primary">Filter</button>
                    </form>

                    <table class="table table-bordered">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Reason</th>
                                <th>From</th>
                                <th>To</th>
                                <th>Status</th>
                                <th>Created By</th>
                                <th>Created At</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (requests != null && !requests.isEmpty()) {
                                for (AbsentRequest req : requests) {
                                    String statusText = "Unknown";
                                    switch (req.getStatus()) {
                                        case 1:
                                            statusText = "In Process";
                                            break;
                                        case 2:
                                            statusText = "Approved";
                                            break;
                                        case 3:
                                            statusText = "Rejected";
                                            break;
}

                            %>
                            <tr>
                                <td><%= req.getAbsenceId() %></td>
                                <td><%= req.getTitle() %></td>
                                <td><%= req.getReason() %></td>
                                <td><%= req.getFromDate() %></td>
                                <td><%= req.getToDate() %></td>
                                <td><%= statusText %></td>
                                <td><%= req.getCreatedBy() %></td>
                                <td><%= req.getCreationDate() %></td>
                                <td>
                                    <% if (req.getStatus() == 1) { %>
                                    <form method="post" action="boss_dashboard" style="display:inline;">
                                        <input type="hidden" name="abid" value="<%= req.getAbsenceId() %>"/>
                                        <input type="hidden" name="action" value="approve"/>
                                        <button class="btn btn-sm btn-success" onclick="return confirm('Duyệt đơn này?')">Approve</button>
                                    </form>
                                    <form method="post" action="boss_dashboard" style="display:inline;">
                                        <input type="hidden" name="abid" value="<%= req.getAbsenceId() %>"/>
                                        <input type="hidden" name="action" value="reject"/>
                                        <button class="btn btn-sm btn-danger" onclick="return confirm('Từ chối đơn này?')">Reject</button>
                                    </form>
                                    <% } else { %>
                                    <em>Đã xử lý</em>
                                    <% } %>
                                </td>
                            </tr>
                            <% } } else { %>
                            <tr><td colspan="9" class="text-center">No requests found.</td></tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
