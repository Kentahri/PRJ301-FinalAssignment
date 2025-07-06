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
public class updateRequestController extends RoleController {

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String action = req.getParameter("action");
        int id = Integer.parseInt(req.getParameter("id"));

        LeaveRequestDBContext db = new LeaveRequestDBContext();

        if ("update".equals(action)) {
            // Lấy dữ liệu chỉnh sửa
            Date fromDate = Date.valueOf(req.getParameter("fromDate"));
            Date toDate = Date.valueOf(req.getParameter("toDate"));
            String reason = req.getParameter("reason");

            LeaveRequest request = new LeaveRequest();
            request.setId(id);
            request.setStartDate(fromDate);
            request.setEndDate(toDate);
            request.setReason(reason);

            db.update(request);

            resp.sendRedirect("myrequest?message=updated");
        } else if ("delete".equals(action)) {
            db.delete(id, account.getId());

            resp.sendRedirect("myrequest?message=deleted");
        } else {
            // Trường hợp không rõ action
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
        }
    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
