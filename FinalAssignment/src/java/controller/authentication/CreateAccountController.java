package controller.authentication;

import dal.AccountDBContext;
import dal.DepartmentDBContext;
import dal.RoleDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Account;
import model.Department;
import model.Role;

public class CreateAccountController extends RoleController {

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        DepartmentDBContext deptDB = new DepartmentDBContext();
        RoleDBContext roleDB = new RoleDBContext();

        ArrayList<Department> departments = deptDB.list();
        ArrayList<Role> roles = roleDB.list();

        req.setAttribute("departments", departments);
        req.setAttribute("roles", roles);

        req.getRequestDispatcher("../website/createAccount.jsp").forward(req, resp);
    }

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmpass = req.getParameter("confirmpassword");
        String displayname = req.getParameter("displayname");
        String employeename = req.getParameter("employeename");
        int departmentId = Integer.parseInt(req.getParameter("department"));

        String[] roleIdsRaw = req.getParameterValues("roles");
        ArrayList<Integer> roleIds = new ArrayList<>();
        for (String r : roleIdsRaw) {
            roleIds.add(Integer.parseInt(r));
        }

        if (!password.equals(confirmpass)) {
            req.setAttribute("error", "Mật khẩu xác nhận không trùng khớp.");
            processGet(req, resp, account);
            return;
        }

        AccountDBContext db = new AccountDBContext();
        boolean success = db.createAccountAndEmployee(username, password, displayname, employeename, departmentId, roleIds);
        if (success) {
            req.setAttribute("message", "Tạo tài khoản thành công!");
            req.getRequestDispatcher("../website/homepage").forward(req, resp);
        } else {
            req.setAttribute("error", "Tạo tài khoản thất bại!");
            processGet(req, resp, account);
        }
    }
}
