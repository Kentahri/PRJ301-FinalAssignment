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
        resp.sendRedirect("myrequest");
    }
}
