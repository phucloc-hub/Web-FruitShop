/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loctp.HanaShop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import loctp.Utils.DBUtils;

/**
 *
 * @author Loc
 */
public class AccountDAO {

    public AccountDTO getExistAccount(String userEmail) throws SQLException, ClassNotFoundException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        AccountDTO dto = null;
        try {
            //1. Make Connection
            con = DBUtils.makeCon();

            if (con != null) {
                //2. tao cau truy van
                String sql = "select Email, Name, Password, Role "
                        + "From Account "
                        + "Where Email = ?";

                //3. Tao statement and set parameter
                prs = con.prepareStatement(sql);
                prs.setString(1, userEmail);

                //4. Execute query
                rs = prs.executeQuery();

                //5. process rs
                if (rs.next()) {// co hay k co. rs luon luon lon hon bang 0. khong co thi =0.
                    String name= rs.getString(2);
                    String password= rs.getString(3);
                    String role= rs.getString(4);
                    dto = new AccountDTO(userEmail, name, password, role);
                }

            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (prs != null) {
                prs.close();
            }
            if (con != null) {
                con.close();
            }

        }
        return dto;
    }

    public void addAccount(String userEmail, String name, String password, String role) throws SQLException, ClassNotFoundException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        try {
            //1. Make Connection
            con = DBUtils.makeCon();

            if (con != null) {
                //2. tao cau truy van

                String sql = "insert into Account(Email,Name,Password,Role)"
                        + " values(?,?,?,?)";
                //3. Tao statement and set parameter
                prs = con.prepareStatement(sql);
                prs.setString(1, userEmail);
                prs.setString(2, name);
                prs.setString(3, password);
                prs.setString(4, role);
                //4. Execute query
                prs.executeUpdate();

                //5. process rs
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (prs != null) {
                prs.close();
            }
            if (con != null) {
                con.close();
            }

        }
    }

    public String checkLogin(String userEmail, String password) throws SQLException, ClassNotFoundException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        String result = "";
        try {
            //1. Make Connection
            con = DBUtils.makeCon();

            if (con != null) {
                //2. tao cau truy van
                String sql = "select Email, Name, Password, Role "
                        + "From Account "
                        + "Where Email = ? AND Password = ?";

                //3. Tao statement and set parameter
                prs = con.prepareStatement(sql);
                prs.setString(1, userEmail);
                prs.setString(2, password);

                //4. Execute query
                rs = prs.executeQuery();

                //5. process rs
                if (rs.next()) {// co hay k co. rs luon luon lon hon bang 0. khong co thi =0.
                    result = rs.getString(2) + ':' + rs.getString(4); // return name and role

                }

            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (prs != null) {
                prs.close();
            }
            if (con != null) {
                con.close();
            }

        }
        return result;
    }

}
