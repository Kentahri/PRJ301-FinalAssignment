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
import java.util.ArrayList;
import model.Account;
import model.LeaveRequest;
import java.sql.Date;

/**
 *
 * @author anhqu
 */
public class myrequestController extends RoleController {

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account)
            throws ServletException, IOException {

        LeaveRequestDBContext db = new LeaveRequestDBContext();
        ArrayList<LeaveRequest> myRequest = db.listCreatedBy(account.getId());

        req.setAttribute("myRequests", myRequest);
        req.getRequestDispatcher("/website/myrequest.jsp").forward(req, resp);
    }

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp, Account account)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        LeaveRequestDBContext db = new LeaveRequestDBContext();

        if ("update".equals(action)) {
            // Cập nhật đơn

            int id = Integer.parseInt(req.getParameter("id"));
            String fromStr = req.getParameter("fromDate");
            String toStr = req.getParameter("toDate");
            String reason = req.getParameter("reason");

            if (fromStr == null || toStr == null || fromStr.isEmpty() || toStr.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ngày bắt đầu và ngày kết thúc không được bỏ trống");
                return;
            }

            Date fromDate = Date.valueOf(fromStr);
            Date toDate = Date.valueOf(toStr);

            // Kiểm tra fromDate < toDate
            if (!fromDate.before(toDate)) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ngày kết thúc phải sau ngày bắt đầu");
                return;
            }

            // Kiểm tra fromDate >= hôm nay + 1 ngày
            Date today = new Date(System.currentTimeMillis());
            Date minDate = new Date(today.getTime() + 24L * 60 * 60 * 1000);
            if (fromDate.before(minDate)) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ngày bắt đầu phải ít nhất sau 24 giờ kể từ bây giờ");
                return;
            }

            LeaveRequest request = new LeaveRequest();
            request.setId(id);
            request.setStartDate(fromDate);
            request.setEndDate(toDate);
            request.setReason(reason);

            db.update(request);

            resp.sendRedirect("myrequest?message=updated");
        } else if ("delete".equals(action)) {
            // Xoá đơn
            int id = Integer.parseInt(req.getParameter("id"));
            db.delete(id, account.getId());

            resp.sendRedirect("myrequest?message=deleted");
        } else {
            resp.sendRedirect("myrequest");
        }
    }
}
