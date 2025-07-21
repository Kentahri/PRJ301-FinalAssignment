package controller.website;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Account;

public class HomepageController extends HttpServlet {

    private void prepareData(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Account acc = (Account) session.getAttribute("account");
        if (acc == null) {
            return;
        }
        String ename = acc.getEmployee() != null ? acc.getEmployee().getName() : "";
        String welcomeMsg = "Xin chào " + ename + ". Chào mừng bạn quay trở lại làm việc!";
        req.setAttribute("welcomeMessage", welcomeMsg);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Account acc = (Account) session.getAttribute("account");

        if (acc == null) {
            resp.sendRedirect("../login");
            return;
        }

        prepareData(req);

        req.getRequestDispatcher("homepage.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Account acc = (Account) session.getAttribute("account");

        if (acc == null) {
            resp.sendRedirect("../login");
            return;
        }

        prepareData(req);

        req.getRequestDispatcher("../website/homepage.jsp").forward(req, resp);
    }
}
