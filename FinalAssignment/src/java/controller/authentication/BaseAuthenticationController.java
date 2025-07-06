/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.authentication;

/**
 *
 * @author anhqu
 */
import model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author anhqu
 */
public abstract class BaseAuthenticationController extends HttpServlet {

    private Account getAuthenticated(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute("account");
        return account;
    }

    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException;

    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp, Account acccount) throws ServletException, IOException;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = getAuthenticated(req);
        if (account == null) {
            resp.sendRedirect("../login?message=not_logged_in");
        } else {
            doPost(req, resp, account);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = getAuthenticated(req);
        if (account == null) {
            resp.sendRedirect("../login?message=not_logged_in");
        } else {
            doGet(req, resp, account);
        }
    }

}
