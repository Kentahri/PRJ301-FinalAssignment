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
import model.LeaveRequest;
import java.util.ArrayList;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Department;

/**
 *
 * @author anhqu
 */
public class LeaveRequestDBContext extends DBContext<LeaveRequest> {

    public ArrayList<LeaveRequest> listCreatedBy(int aid) {
        ArrayList<LeaveRequest> list = new ArrayList<>();
        try {
            String sql = """
            SELECT 
                lr.id, 
                lr.startDate, 
                lr.endDate, 
                lr.reason, 
                lr.status, 
                lr.note, 
                lr.processedBy, 
                p.username AS processedUser
            FROM LeaveRequest lr
            LEFT JOIN Account p ON lr.processedBy = p.aid
            WHERE lr.createdBy = ?
            ORDER BY lr.startDate DESC
        """;
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, aid);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                LeaveRequest l = new LeaveRequest();
                l.setId(rs.getInt("id"));
                l.setStartDate(rs.getDate("startDate"));
                l.setEndDate(rs.getDate("endDate"));
                l.setReason(rs.getString("reason"));
                l.setStatus(rs.getInt("status"));
                l.setNote(rs.getString("note"));

                int processedId = rs.getInt("processedBy");
                if (!rs.wasNull()) {
                    Account p = new Account();
                    p.setId(processedId);
                    p.setUsername(rs.getString("processedUser"));
                    l.setProcessBy(p);
                }

                list.add(l);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    public ArrayList<LeaveRequest> list(int aid) {
        ArrayList<LeaveRequest> model = new ArrayList<>();
        try {
            String sql = "WITH EmployeeHierarchy AS (\n"
                    + "    SELECT e.eid\n"
                    + "    FROM Employee e\n"
                    + "    INNER JOIN Account_Employee ae ON ae.eid = e.eid\n"
                    + "    WHERE ae.aid = ?\n"
                    + "    UNION ALL\n"
                    + "    SELECT e.eid\n"
                    + "    FROM Employee e\n"
                    + "    INNER JOIN EmployeeHierarchy eh ON e.managerid = eh.eid\n"
                    + ")\n"
                    + "SELECT \n"
                    + "    model.id,\n"
                    + "    model.reason,\n"
                    + "    model.startDate,\n"
                    + "    model.endDate,\n"
                    + "    model.status,\n"
                    + "    model.createdBy,\n"
                    + "    creator.displayname AS createdUser,\n"
                    + "    model.processedBy,\n"
                    + "    processor.displayname AS processedUser,\n"
                    + "    model.note,\n"
                    + "    d.id AS depId,\n"
                    + "    d.name AS depName\n"
                    + "FROM EmployeeHierarchy eh\n"
                    + "INNER JOIN Account_Employee ae ON ae.eid = eh.eid\n"
                    + "INNER JOIN Account creator ON creator.aid = ae.aid\n"
                    + "INNER JOIN LeaveRequest model ON model.createdBy = creator.aid\n"
                    + "LEFT JOIN Account processor ON processor.aid = model.processedBy\n"
                    + "INNER JOIN Account_Employee ae_creator ON ae_creator.aid = creator.aid\n"
                    + "INNER JOIN Employee e_creator ON e_creator.eid = ae_creator.eid\n"
                    + "INNER JOIN Department d ON d.id = e_creator.did\n"
                    + "WHERE creator.aid != ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, aid);
            stm.setInt(2, aid);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                LeaveRequest r = new LeaveRequest();
                r.setId(rs.getInt("id"));
                r.setReason(rs.getString("reason"));
                r.setStartDate(rs.getDate("startDate"));
                r.setEndDate(rs.getDate("endDate"));
                r.setStatus(rs.getInt("status"));

                Account createdBy = new Account();
                createdBy.setId(rs.getInt("createdBy"));
                createdBy.setUsername(rs.getString("createdUser"));
                r.setCreatedBy(createdBy);

                int processedId = rs.getInt("processedBy");
                if (!rs.wasNull()) {
                    Account processedBy = new Account();
                    processedBy.setId(processedId);
                    processedBy.setUsername(rs.getString("processedUser"));
                    r.setProcessBy(processedBy);
                }

                r.setNote(rs.getString("note"));
                Department dep = new Department();
                dep.setId(rs.getInt("depId"));
                dep.setName(rs.getString("depName"));
                r.setDepartment(dep);
                model.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return model;
    }

    public void approveRequest(int requestId, int processorId, String note) {
        try {
            String sql = "UPDATE LeaveRequest SET status = 1, processedBy = ?, note = ? WHERE id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, processorId);
            stm.setString(2, note);
            stm.setInt(3, requestId);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void rejectRequest(int requestId, int processorId, String note) {
        try {
            String sql = "UPDATE LeaveRequest SET status = 2, processedBy = ?, note = ? WHERE id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, processorId);
            stm.setString(2, note);
            stm.setInt(3, requestId);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void delete(int id, int accountId) {
        try {
            String sql = "DELETE FROM LeaveRequest WHERE id = ? AND createdBy = ? AND status = 0";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.setInt(2, accountId);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateStatus(int requestId, int status, int processedBy, String note) {
        try {
            String sql = "UPDATE LeaveRequest "
                    + "SET status = ?, "
                    + "processedBy = ?, "
                    + "note = ?, "
                    + "updatedAt = GETDATE() "
                    + "WHERE id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, status);
            stm.setInt(2, processedBy);
            stm.setString(3, note);
            stm.setInt(4, requestId);

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Map<Date, Map<Integer, String>> getAttendanceStatus(int departmentId, Date from, Date to) {
        Map<Date, Map<Integer, String>> result = new LinkedHashMap<>();
        try {
            // Lấy danh sách nhân viên
            String empSql = "SELECT e.eid, e.ename "
                    + "FROM Employee e "
                    + "WHERE e.did = ?";
            PreparedStatement empStm = connection.prepareStatement(empSql);
            empStm.setInt(1, departmentId);
            ResultSet empRs = empStm.executeQuery();

            // Tạo map nhân viên
            Map<Integer, String> employees = new LinkedHashMap<>();
            while (empRs.next()) {
                employees.put(empRs.getInt("eid"), empRs.getString("ename"));
            }

            // Tạo danh sách ngày
            long millisPerDay = 24 * 60 * 60 * 1000;
            for (long time = from.getTime(); time <= to.getTime(); time += millisPerDay) {
                result.put(new Date(time), new HashMap<>());
            }

            // Lấy danh sách đơn nghỉ phép
            String leaveSql = "SELECT e.eid, lr.startDate, lr.endDate, lr.status\n"
                    + "    FROM Employee e\n"
                    + "    JOIN Account_Employee ae ON e.eid = ae.eid\n"
                    + "    JOIN LeaveRequest lr ON lr.createdBy = ae.aid\n"
                    + "    WHERE e.did = ? AND lr.status IN (1,2)";
            PreparedStatement leaveStm = connection.prepareStatement(leaveSql);
            leaveStm.setInt(1, departmentId);
            ResultSet leaveRs = leaveStm.executeQuery();

            // Gán trạng thái dựa trên status
            while (leaveRs.next()) {
                int eid = leaveRs.getInt("eid");
                Date start = leaveRs.getDate("startDate");
                Date end = leaveRs.getDate("endDate");
                int status = leaveRs.getInt("status");

                for (Date d : result.keySet()) {
                    if (!d.before(start) && !d.after(end)) {
                        if (status == 1) {
                            result.get(d).put(eid, "leave"); // đã duyệt
                        } else if (status == 2) {
                            result.get(d).put(eid, "rejected"); // không duyệt
                        }
                    }
                }
            }
            
            // Gán trạng thái đi làm hoặc tương lai
            Date today = new Date(System.currentTimeMillis());
            for (Date d : result.keySet()) {
                Map<Integer, String> dayMap = result.get(d);
                for (Integer eid : employees.keySet()) {
                    if (!dayMap.containsKey(eid)) {
                        if (d.after(today)) {
                            dayMap.put(eid, "future");
                        } else {
                            dayMap.put(eid, "present");
                        }
                    }
                }
            }

            // Đóng kết nối
            empRs.close();
            empStm.close();
            leaveRs.close();
            leaveStm.close();
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

// Lấy danh sách nhân viên để render cột header
    public Map<Integer, String> getEmployeesInDepartment(int departmentId) {
        Map<Integer, String> employees = new LinkedHashMap<>();
        try {
            String sql = "SELECT eid, ename FROM Employee WHERE did = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, departmentId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                employees.put(rs.getInt("eid"), rs.getString("ename"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employees;
    }

    @Override
    public ArrayList<LeaveRequest> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public LeaveRequest get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(LeaveRequest model) {
        try {
            String sql = "INSERT INTO LeaveRequest "
                    + "(startDate, endDate, reason, status, createdby, processedby, note) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setDate(1, model.getStartDate());
            stm.setDate(2, model.getEndDate());
            stm.setString(3, model.getReason());
            stm.setInt(4, model.getStatus());
            stm.setInt(5, model.getCreatedBy().getId());

            if (model.getProcessBy() != null) {
                stm.setInt(6, model.getProcessBy().getId());
            } else {
                stm.setNull(6, java.sql.Types.INTEGER);
            }

            if (model.getNote() != null) {
                stm.setString(7, model.getNote());
            } else {
                stm.setNull(7, java.sql.Types.NVARCHAR);
            }

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(LeaveRequest model) {
        try {
            String sql = "UPDATE LeaveRequest SET startDate = ?, endDate = ?, reason = ? WHERE id = ? AND status = 0";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setDate(1, model.getStartDate());
            stm.setDate(2, model.getEndDate());
            stm.setString(3, model.getReason());
            stm.setInt(4, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(int id) {
    }

}
