/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author anhqu
 */
import model.Account;
import model.Employee;
import model.Department;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anhqu
 */
public class AccountDBContext extends DBContext<Account> {

    public boolean isExist(String username) {
        try {
            String sql = "SELECT COUNT(*) FROM Account WHERE username = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Account login(String username, String password) {
        try {
            String sql = " SELECT \n"
                    + "                a.aid, \n"
                    + "                a.username, \n"
                    + "                a.displayname,\n"
                    + "                e.eid,\n"
                    + "                e.ename,\n"
                    + "                e.did,\n"
                    + "                d.name AS dname\n"
                    + "            FROM Account a\n"
                    + "            LEFT JOIN Account_Employee ea ON a.aid = ea.aid\n"
                    + "            LEFT JOIN Employee e ON ea.eid = e.eid\n"
                    + "            LEFT JOIN Department d ON e.did = d.id\n"
                    + "            WHERE a.username = ? AND a.password = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("aid"));
                account.setUsername(rs.getString("username"));
                account.setDisplayname(rs.getString("displayname"));

                int eid = rs.getInt("eid");
                if (!rs.wasNull()) {
                    Employee emp = new Employee();
                    emp.setId(eid);
                    emp.setName(rs.getString("ename"));

                    Department dep = new Department();
                    dep.setId(rs.getInt("did"));
                    dep.setName(rs.getString("dname"));

                    emp.setDepartment(dep);
                    account.setEmployee(emp);
                }

                return account;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void insertRoleForAccount(int aid, int rid) {
        try {
            String sql = "INSERT INTO Account_Role(aid, rid) VALUES (?, ?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, aid);
            stm.setInt(2, rid);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Account> getAllAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT aid, username, displayname FROM Account";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setId(rs.getInt("aid"));
                acc.setUsername(rs.getString("username"));
                acc.setDisplayname(rs.getString("displayname"));
                accounts.add(acc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return accounts;
    }

    @Override
    public ArrayList<Account> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Account get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Account account) {

    }

    @Override
    public void update(Account model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
