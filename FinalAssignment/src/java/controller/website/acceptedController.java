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

/**
 *
 * @author anhqu
 */
public class acceptedController extends RoleController {

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        int requestId = Integer.parseInt(req.getParameter("id"));
        String action = req.getParameter("action");
        String note = req.getParameter("note");

        LeaveRequestDBContext db = new LeaveRequestDBContext();

        if ("approve".equals(action)) {
            db.approveRequest(requestId, account.getId(), note);
        } else if ("reject".equals(action)) {
            db.rejectRequest(requestId, account.getId(), note);
        }

        // Sau khi xử lý xong, quay lại trang danh sách
        resp.sendRedirect("accepted");
    }

@Override
protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        LeaveRequestDBContext db = new LeaveRequestDBContext();
        ArrayList<LeaveRequest> requests = db.list(account.getId());
        req.setAttribute("requests", requests);
        req.getRequestDispatcher("../website/accepted.jsp").forward(req, resp);
    }

}
