/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.authentication;

/**
 *
 * @author anhqu
 */
import dal.AccountDBContext;
import dal.RoleDBContext;
import model.Account;
import model.Feature;
import model.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author anhqu
 */
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        String pass = req.getParameter("pass");

        AccountDBContext db = new AccountDBContext();
        Account account = db.login(user, pass);
        if (account == null) {
            req.setAttribute("message", "fail");
            req.getRequestDispatcher("website/login.jsp").forward(req, resp);
        } else {
            RoleDBContext roleDB = new RoleDBContext();
            ArrayList<Role> roles = roleDB.importRoles(account.getId());
            account.setRoles(roles);

            // Tạo danh sách quyền
            Set<String> allowedEntrypoints = new HashSet<>();
            for (Role role : roles) {
                for (Feature feature : role.getFeatures()) {
                    allowedEntrypoints.add(feature.getEntrypoint());
                }
            }

            // Lưu vào session
            HttpSession session = req.getSession();
            session.setAttribute("account", account);
            session.setAttribute("allowedEntrypoints", allowedEntrypoints);

            resp.sendRedirect("website/homepage.jsp");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("website/login.jsp").forward(req, resp);
    }

}
