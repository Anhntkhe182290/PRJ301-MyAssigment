package controller;

import dal.AbsentRequestDBContext;
import data.AbsentRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@WebServlet("/agenda")
public class AgendaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String monthParam = request.getParameter("month");
        String yearParam = request.getParameter("year");

        LocalDate today = LocalDate.now();
        int month = (monthParam != null) ? Integer.parseInt(monthParam) : today.getMonthValue();
        int year = (yearParam != null) ? Integer.parseInt(yearParam) : today.getYear();

        // Tính khoảng thời gian trong tháng
        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        AbsentRequestDBContext db = new AbsentRequestDBContext();
        List<AbsentRequest> approvedRequests = db.getApprovedRequestsInRange(start.toString(), end.toString());

        // Map<LocalDate, List<Username>>
        Map<LocalDate, List<String>> absenceMap = new HashMap<>();
        Set<String> userSet = new HashSet<>();

        for (AbsentRequest req : approvedRequests) {
            String username = req.getCreatedBy();
            userSet.add(username);

            LocalDate from = LocalDate.parse(req.getFromDate());
            LocalDate to = LocalDate.parse(req.getToDate());

            while (!from.isAfter(to)) {
                if (from.getMonthValue() == month && from.getYear() == year) {
                    absenceMap.computeIfAbsent(from, k -> new ArrayList<>()).add(username);
                }
                from = from.plusDays(1);
            }
        }

        request.setAttribute("absenceMap", absenceMap);
        request.setAttribute("selectedMonth", month);
        request.setAttribute("selectedYear", year);
        request.setAttribute("allUsers", new ArrayList<>(userSet));

        request.getRequestDispatcher("agenda.jsp").forward(request, response);
    }
}