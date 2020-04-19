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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import loctp.Utils.DBUtils;

/**
 *
 * @author Loc
 */
public class OrderDAO {

    private List<OrderDTO> listDTO;

    public List<OrderDTO> getListDTO() {
        return listDTO;
    }

    //String email, String productName, int quantity, float total, String buyDate, float price
    public void searchOrderByName(String email, String productName) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        listDTO = new ArrayList<>();
        try {
            con = DBUtils.makeCon();

            String sql = "select ProductName,Quantity,Total,BuyDate,Price from [Order] where Email = ? AND ProductName LIKE ?";

            prs = con.prepareStatement(sql);
            prs.setString(1, email);
            prs.setString(2, "%" + productName + "%");

            rs = prs.executeQuery();
            while (rs.next()) {
                String ProductName = rs.getString(1);
                int quantity = rs.getInt(2);
                float total = rs.getFloat(3);
                String buyDate = rs.getString(4);
                float price = rs.getFloat(5);

                listDTO.add(new OrderDTO(email, ProductName, quantity, total, buyDate, price));

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

    public void searchOrderByDate(String email, String buyDate) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        listDTO = new ArrayList<>();
        try {
            con = DBUtils.makeCon();

//            String sql = "select ProductName,Quantity,Total,BuyDate,Price from [Order] where Email = ? AND BuyDate = ?";
            String sqln = "select ProductName,Quantity,Total,BuyDate,Price from [Order] where Email = ? AND "
                    + " BuyDate >=  convert(datetime,?,101) AND"
                    + " BuyDate <= convert(datetime,?,101) AND ISDATE(BuyDate) = 1";
            prs = con.prepareStatement(sqln);
            prs.setString(1, email);
            prs.setString(2,buyDate+" 00:00:00");
            prs.setString(3,buyDate+" 23:59:59");

            rs = prs.executeQuery();
            while (rs.next()) {
                String ProductName = rs.getString(1);
                int quantity = rs.getInt(2);
                float total = rs.getFloat(3);
                String BuyDate = rs.getString(4);
                float price = rs.getFloat(5);

                listDTO.add(new OrderDTO(email, ProductName, quantity, total, buyDate, price));

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

    //String email, String productName, int quantity, float total, String buyDate, float price
    public void insertOrder(String email, String productName, int quantity, float total, String buyDate, float price,String payment) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;

        try {

            con = DBUtils.makeCon();

            String sql = "insert into [Order](Email,ProductName,Quantity,Total,BuyDate,Price,Payment) Values(?,?,?,?,?,?,?)";

            prs = con.prepareStatement(sql);
            prs.setString(1, email);
            prs.setString(2, productName);
            prs.setInt(3, quantity);
            prs.setFloat(4, total);
            prs.setString(5, buyDate);
            prs.setFloat(6, price);
            prs.setString(7, payment);

            prs.executeUpdate();

        } finally {
            if (prs != null) {
                prs.close();
            }
            if (con != null) {
                con.close();
            }
        }

    }

}
