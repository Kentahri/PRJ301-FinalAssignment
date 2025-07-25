/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.authentication;

/**
 *
 * @author anhqu
 */
import dal.RoleDBContext;
import model.Account;
import model.Feature;
import model.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author anhqu
 */
public abstract class RoleController extends BaseAuthenticationController {

    private boolean isGrantedAccessControl(HttpServletRequest req, Account account) {
        String current_access_entrypoint = req.getServletPath();

        Set<String> allowedEntrypoints = (Set<String>) req.getSession().getAttribute("allowedEntrypoints");
        if (allowedEntrypoints == null) {
            return false;
        }

        return allowedEntrypoints.contains(current_access_entrypoint);
    }

    protected abstract void processPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException;

    protected abstract void processGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        if (isGrantedAccessControl(req, account)) {
            processPost(req, resp, account);
        } else {
            req.setAttribute("message", "Bạn không có quyền truy cập chức năng này!");
            req.getRequestDispatcher("/website/homepage.jsp").forward(req, resp);

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        if (isGrantedAccessControl(req, account)) {
            processGet(req, resp, account);
        } else {
            req.setAttribute("message", "Bạn không có quyền truy cập chức năng này!");
            req.getRequestDispatcher("/website/homepage.jsp").forward(req, resp);
        }
    }
}
