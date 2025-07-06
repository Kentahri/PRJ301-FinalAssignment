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

        String fromDate = req.getParameter("fromDate");
        String toDate = req.getParameter("toDate");
        String reason = req.getParameter("reason");

        LeaveRequest leave = new LeaveRequest();
        leave.setStartDate(Date.valueOf(fromDate));
        leave.setEndDate(Date.valueOf(toDate));
        leave.setReason(reason);
        leave.setStatus(0); // 0: chờ duyệt
        leave.setCreatedBy(account);
        leave.setProcessBy(null); // chưa duyệt
        leave.setNote(null);      // chưa có ghi chú từ người duyệt

        LeaveRequestDBContext db = new LeaveRequestDBContext();
        db.insert(leave);

        req.setAttribute("message", "Bạn đã tạo đơn nghỉ phép thành công!");
        req.getRequestDispatcher("homepage.jsp").forward(req, resp);
    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account)
            throws ServletException, IOException {
        req.getRequestDispatcher("/website/create.jsp").forward(req, resp);
    }
}
