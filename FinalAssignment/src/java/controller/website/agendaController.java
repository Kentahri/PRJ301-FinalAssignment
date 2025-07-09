/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.website;

import controller.authentication.RoleController;
import dal.LeaveRequestDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Map;
import model.Account;

/**
 *
 * @author anhqu
 */
public class agendaController extends RoleController {

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String fromStr = req.getParameter("fromDate");
        String toStr = req.getParameter("toDate");

        if (fromStr == null || toStr == null || fromStr.isEmpty() || toStr.isEmpty()) {
            req.setAttribute("error", "Vui lòng chọn khoảng ngày");
            req.getRequestDispatcher("../website/agenda.jsp").forward(req, resp);
            return;
        }

        // Kiểm tra xem account có nhân viên và phòng ban không
        if (account.getEmployee() == null || account.getEmployee().getDepartment() == null) {
            req.setAttribute("error", "Tài khoản của bạn chưa được liên kết với nhân viên hoặc phòng ban.");
            req.getRequestDispatcher("../website/agenda.jsp").forward(req, resp);
            return;
        }

        Date from = Date.valueOf(fromStr);
        Date to = Date.valueOf(toStr);

        if (from.after(to)) {
            req.setAttribute("error", "Ngày bắt đầu không được lớn hơn ngày kết thúc.");
            req.getRequestDispatcher("agenda.jsp").forward(req, resp);
            return; // Dừng xử lý tiếp
        }

        int departmentId = account.getEmployee().getDepartment().getId();

        LeaveRequestDBContext db = new LeaveRequestDBContext();
        Map<Date, Map<Integer, String>> statusMap = db.getAttendanceStatus(departmentId, from, to);
        Map<Integer, String> employees = db.getEmployeesInDepartment(departmentId);

        req.setAttribute("statuses", statusMap);
        req.setAttribute("employees", employees);
        req.setAttribute("fromDate", fromStr);
        req.setAttribute("toDate", toStr);
        req.getRequestDispatcher("../website/agenda.jsp").forward(req, resp);
    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        req.getRequestDispatcher("../website/agenda.jsp").forward(req, resp);
    }

}
