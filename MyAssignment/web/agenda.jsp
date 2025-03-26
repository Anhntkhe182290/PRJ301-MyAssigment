<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.*, java.util.*" %>
<%
    Map<LocalDate, List<String>> absenceMap = (Map<LocalDate, List<String>>) request.getAttribute("absenceMap");
    if (absenceMap == null) absenceMap = new HashMap<>();

    Integer year = (Integer) request.getAttribute("selectedYear");
    Integer month = (Integer) request.getAttribute("selectedMonth");
    List<String> allUsers = (List<String>) request.getAttribute("allUsers");
    if (allUsers == null) allUsers = new ArrayList<>();

    // Nếu null thì fallback về thời gian hiện tại
    if (year == null || month == null) {
        LocalDate now = LocalDate.now();
        year = now.getYear();
        month = now.getMonthValue();
    }

    YearMonth ym = YearMonth.of(year, month);
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Lịch nghỉ theo tháng</title>
        <link rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css">
        <style>
            .highlight {
                background-color: #ffdddd;
            }
            .sticky-col {
                position: sticky;
                left: 0;
                background-color: #f9f9f9;
                z-index: 1;
            }
            table {
                overflow-x: auto;
            }
        </style>
    </head>
    <body class="container mt-4">
        <h4>📅 Lịch nghỉ nhân viên tháng <%= month %>/<%= year %></h4>

        <form method="get" action="agenda" class="form-inline mb-3">
            <label class="mr-2">Chọn tháng:</label>
            <select name="month" class="form-control mr-3">
                <% for (int i = 1; i <= 12; i++) { %>
                <option value="<%= i %>" <%= (i == month) ? "selected" : "" %>><%= i %></option>
                <% } %>
            </select>

            <label class="mr-2">Chọn năm:</label>
            <select name="year" class="form-control mr-3">
                <% for (int i = 2023; i <= 2025; i++) { %>
                <option value="<%= i %>" <%= (i == year) ? "selected" : "" %>><%= i %></option>
                <% } %>
            </select>

            <button type="submit" class="btn btn-primary">Lọc</button>
            <a href="boss_dashboard" class="btn btn-default ml-3">⬅ Quay lại</a>
        </form>

        <div class="table-responsive">
            <table class="table table-bordered text-center">
                <thead>
                    <tr>
                        <th class="sticky-col">👤 Username</th>
                            <% for (int d = 1; d <= ym.lengthOfMonth(); d++) { %>
                        <th><%= d %></th>
                            <% } %>
                    </tr>
                </thead>
                <tbody>
                    <% for (String username : allUsers) { %>
                    <tr>
                        <td class="sticky-col"><%= username %></td>
                        <% for (int d = 1; d <= ym.lengthOfMonth(); d++) {
                            LocalDate date = ym.atDay(d);
                            boolean isAbsent = absenceMap.containsKey(date) && absenceMap.get(date).contains(username);
                        %>
                        <td class="<%= isAbsent ? "highlight" : "" %>"><%= isAbsent ? "X" : "" %></td>
                        <% } %>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="plugins/bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
