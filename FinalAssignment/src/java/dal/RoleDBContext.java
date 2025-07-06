/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author anhqu
 */
import model.Feature;
import model.Role;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anhqu
 */
public class RoleDBContext extends DBContext<Role>{
    
    public ArrayList<Role> importRoles(int aid) {
        ArrayList<Role> roles = new ArrayList<>();
        try {
            String sql = "SELECT r.rid,r.rname,f.fid,f.description,f.entrypoint FROM \n"
                    + "	Account a LEFT JOIN Account_Role ar ON a.aid = ar.aid\n"
                    + "			  LEFT JOIN [Role] r ON r.rid = ar.rid\n"
                    + "			  LEFT JOIN Role_Feature rf ON rf.rid = r.rid\n"
                    + "			  LEFT JOIN Feature f ON f.fid = rf.fid\n"
                    + "WHERE \n"
                    + "a.aid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, aid);
            ResultSet rs = stm.executeQuery();
            
            Role temp = new Role();
            temp.setId(-1);
            
            while(rs.next())
            {
                int rid = rs.getInt("rid");
                if(rid != temp.getId())
                {
                    temp = new Role();
                    temp.setId(rid);
                    temp.setName(rs.getString("rname"));
                    roles.add(temp);
                }
                
                int fid = rs.getInt("fid");
                if(fid > 0)
                {
                    Feature f = new Feature();
                    f.setId(fid);
                    f.setDescription(rs.getString("description"));
                    f.setEntrypoint(rs.getString("entrypoint"));
                    temp.getFeatures().add(f);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try {
                if(!connection.isClosed())connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(RoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return roles;
    }

    @Override
    public ArrayList<Role> list() {
        ArrayList<Role> roles = new ArrayList<>();
        try {
            String sql = "SELECT rid, rname FROM [Role]";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Role r = new Role();
                r.setId(rs.getInt("rid"));
                r.setName(rs.getString("rname"));
                roles.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return roles;
    }

    @Override
    public Role get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Role model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Role model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
