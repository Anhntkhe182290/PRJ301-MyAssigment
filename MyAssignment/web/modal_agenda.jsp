<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="data.AbsentRequest" %>
<%@ page import="java.time.LocalDate" %>

<%
    List<AbsentRequest> requests = (List<AbsentRequest>) request.getAttribute("requests");

    if (requests != null) {
        Set<String> shownUsers = new HashSet<>();
        for (AbsentRequest req : requests) {
            String username = req.getCreatedBy();

            if (req.getStatus() == 1 && !shownUsers.contains(username)) {
                shownUsers.add(username);
%>
<!-- Modal hiển thị lịch nghỉ của nhân viên -->
<div class="modal fade" id="agendaModal_<%= username %>" tabindex="-1" role="dialog" aria-labelledby="agendaModalLabel_<%= username %>">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <!-- Nút đóng -->
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="agendaModalLabel_<%= username %>">Lịch nghỉ trong tháng của <%= username %></h4>
            </div>
            <div class="modal-body">
                <ul>
                    <%
                        LocalDate now = LocalDate.now();
                        for (AbsentRequest r : requests) {
                            if (r.getCreatedBy().equals(username) && r.getStatus() == 2) {
                                LocalDate from = LocalDate.parse(r.getFromDate());
                                LocalDate to = LocalDate.parse(r.getToDate());
                                while (!from.isAfter(to)) {
                                    if (from.getMonthValue() == now.getMonthValue() && from.getYear() == now.getYear()) {
                    %>
                        <li><span class="text-danger"><strong><%= from %></strong></span></li>
                    <%
                                    }
                                    from = from.plusDays(1);
                                }
                            }
                        }
                    %>
                </ul>
            </div>
        </div>
    </div>
</div>
<%
            }
        }
    }
%>
