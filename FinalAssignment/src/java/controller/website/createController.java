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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import model.Account;
import model.LeaveRequest;

/**
 *
 * @author anhqu
 */
public class createController extends RoleController {

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp, Account account)
            throws ServletException, IOException {

        String rawFromDate = req.getParameter("fromDate");
        String rawToDate = req.getParameter("toDate");
        String reason = req.getParameter("reason");

        try {
            // Chuyển sang LocalDate để dễ kiểm tra
            LocalDate startDate = LocalDate.parse(rawFromDate);
            LocalDate endDate = LocalDate.parse(rawToDate);

            // Thời điểm hiện tại
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime minStartDateTime = now.plusHours(24);
            LocalDate minStartDate = minStartDateTime.toLocalDate();

            // Ràng buộc startDate >= minStartDate (ít nhất sau 24h)
            if (startDate.isBefore(minStartDate)) {
                req.setAttribute("error", "Ngày bắt đầu phải sau ngày hiện tại ít nhất 1 ngày");
                req.getRequestDispatcher("/website/create.jsp").forward(req, resp);
                return;
            }

            // Ràng buộc endDate > startDate
            if (!endDate.isAfter(startDate)) {
                req.setAttribute("error", "Ngày kết thúc phải sau ngày bắt đầu.");
                req.getRequestDispatcher("/website/create.jsp").forward(req, resp);
                return;
            }

            // Nếu hợp lệ thì lưu vào DB
            LeaveRequest leave = new LeaveRequest();
            leave.setStartDate(Date.valueOf(startDate));
            leave.setEndDate(Date.valueOf(endDate));
            leave.setReason(reason);
            leave.setStatus(0); // chờ duyệt
            leave.setCreatedBy(account);
            leave.setProcessBy(null);
            leave.setNote(null);

            LeaveRequestDBContext db = new LeaveRequestDBContext();
            db.insert(leave);

            req.setAttribute("message", "Bạn đã tạo đơn nghỉ phép thành công!");
            req.getRequestDispatcher("homepage").forward(req, resp);

        } catch (DateTimeParseException ex) {
            req.setAttribute("error", "Định dạng ngày không hợp lệ.");
            req.getRequestDispatcher("/website/create.jsp").forward(req, resp);
        }
    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account)
            throws ServletException, IOException {
        req.getRequestDispatcher("/website/create.jsp").forward(req, resp);
    }
}
