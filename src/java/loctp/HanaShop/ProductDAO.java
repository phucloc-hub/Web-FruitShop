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
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import loctp.Utils.DBUtils;

/**
 *
 * @author Loc
 */
public class ProductDAO {

    List<ProductDTO> listProductDTO;

    public List<ProductDTO> getListProductDTO() {
        return listProductDTO;
    }
    
    
    
    // ADMIN
      public boolean isExistProduct(String Name) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs=null;
        boolean check =false;
        //select Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity 
        try {
            con = DBUtils.makeCon();

            String sql="select Name from Product where Name = ?";    
            prs = con.prepareStatement(sql);
            prs.setString(1, Name);
            
            rs= prs.executeQuery();
            if(rs.next()){
                check=true;
            }
            

            

        } finally {
        
            if (prs != null) {
                prs.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return check;
    }
    
      
    
    
    public void addProduct(String Name,String Image,String Description,float Price,String CreateDate,String CategoryID,String Status,int Quantity) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;
        //select Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity 
        try {
            con = DBUtils.makeCon();

            String sql = "Insert into Product (Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity) values(?,?,?,?,?,?,?,?);";
            prs = con.prepareStatement(sql);
            prs.setString(1, Name);
            prs.setString(2, Image);
            prs.setString(3, Description);
            prs.setFloat(4, Price);
            prs.setString(5, CreateDate);
            prs.setString(6, CategoryID);
            prs.setString(7, Status);
            prs.setInt(8, Quantity);
            
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
    
    
    // By combobox
    public boolean searchByAdmin(String CategoryName,String Status) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        boolean hasResult = false;
        int flag = 0;

        // status= active, quantity > 0 ; on name or range of money or category
        listProductDTO = new ArrayList<>();
        try {
            con = DBUtils.makeCon();

//            String sql = "select Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity from Product where Status=?";
            String sql = "select Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity "
                    + " from Product "
                    + " where Status=? AND "
                    + "  CategoryID= (select c.ID from Category c where c.Category LIKE ? ) "
                    + " order by CreateDate DESC";
            prs = con.prepareStatement(sql);
            prs.setString(1, Status);
            prs.setString(2, CategoryName);

            rs = prs.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String image = rs.getString(2);
                String descrip = rs.getString(3);
                float price = rs.getFloat(4);
                String createDate = rs.getString(5);
                String categoryID = rs.getString(6);
                String status = rs.getString(7);
                int quantity = rs.getInt(8);
                listProductDTO.add(new ProductDTO(name, image, descrip, price, createDate, categoryID, status, quantity));
                if (flag == 0) {
                    hasResult = true;
                }
                flag++; // flag de chan khong phai set hasResult=true nhieu lan

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
        return hasResult;
    }
    
    
    public int getQuantity(String productName) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        int Quantity=0;
        try {
            con = DBUtils.makeCon();

            String sql = "select Quantity from Product where Name = ?";

            prs = con.prepareStatement(sql);
            prs.setString(1, productName);

            rs = prs.executeQuery();
            while (rs.next()) {
                 Quantity = rs.getInt(1);
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
        return Quantity;
    }
    
    
    
     public void updateQuantity(String Name,int Quantity) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;

        try {
            con = DBUtils.makeCon();

            String sql = "update Product set Quantity=Quantity-? where Name=?";
            prs = con.prepareStatement(sql);
            
            prs.setInt(1, Quantity);
            prs.setString(2, Name);
            
          
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
    
    
    public void updateProduct(String Name,String Image,String Description,float Price,String CategoryID,String Status,int Quantity) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;

        try {
            con = DBUtils.makeCon();

            String sql = "Update Product Set Image=?,Description=?,Price=?,CategoryID=?,Status=?,Quantity=? where Name= ?";
            prs = con.prepareStatement(sql);
            
            prs.setString(1, Image);
            prs.setString(2, Description);
            prs.setFloat(3, Price);
            prs.setString(4, CategoryID);
            prs.setString(5, Status);
            prs.setInt(6, Quantity);
            
            prs.setString(7, Name);
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
    
    
    public void deleteProducts(String Name) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;

        try {
            con = DBUtils.makeCon();

            String sql = "Update Product Set Status='Inactive' where Name= ?";
            prs = con.prepareStatement(sql);
            prs.setString(1, Name);

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

    
    
    
    //USER
    // By combobox
    public boolean searchCategoryByUser(String CategoryName) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        boolean hasResult = false;
        int flag = 0;

        // status= active, quantity > 0 ; on name or range of money or category
        listProductDTO = new ArrayList<>();
        try {
            con = DBUtils.makeCon();

//            String sql = "select Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity from Product where Status=?";
            String sql = "select Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity "
                    + " from Product "
                    + " where Status=? AND Quantity > 0 AND "
                    + "  CategoryID IN (select c.ID from Category c where c.Category LIKE ? ) "
                    + " order by CreateDate DESC";
            prs = con.prepareStatement(sql);
            prs.setString(1, "Active");
            prs.setString(2, "%" + CategoryName + "%");

            rs = prs.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String image = rs.getString(2);
                String descrip = rs.getString(3);
                float price = rs.getFloat(4);
                String createDate = rs.getString(5);
                String categoryID = rs.getString(6);
                String status = rs.getString(7);
                int quantity = rs.getInt(8);
                listProductDTO.add(new ProductDTO(name, image, descrip, price, createDate, categoryID, status, quantity));
                if (flag == 0) {
                    hasResult = true;
                }
                flag++; // flag de chan khong phai set hasResult=true nhieu lan

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
        return hasResult;
    }

    // By text input
    public boolean searchRanceByUser(float Pricefrom, float Toprice) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        boolean hasResult = false;
        int flag = 0;

        // status= active, quantity > 0 ; on name or range of money or category
        listProductDTO = new ArrayList<>();
        try {
            con = DBUtils.makeCon();

//            String sql = "select Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity from Product where Status=?";
            String sql = "select Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity "
                    + " from Product "
                    + " where Status=? AND Quantity > 0 AND "
                    + " Price >= ? AND Price <= ? "
                    + " order by CreateDate DESC";
            prs = con.prepareStatement(sql);
            prs.setString(1, "Active");
            prs.setFloat(2, Pricefrom);
            prs.setFloat(3, Toprice);

            rs = prs.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String image = rs.getString(2);
                String descrip = rs.getString(3);
                float price = rs.getFloat(4);
                String createDate = rs.getString(5);
                String categoryID = rs.getString(6);
                String status = rs.getString(7);
                int quantity = rs.getInt(8);
                listProductDTO.add(new ProductDTO(name, image, descrip, price, createDate, categoryID, status, quantity));
                if (flag == 0) {
                    hasResult = true;
                }
                flag++; // flag de chan khong phai set hasResult=true nhieu lan

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
        return hasResult;
    }

    // By text input
    public boolean searchNameByUser(String Name) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        boolean hasResult = false;
        int flag = 0;

        // status= active, quantity > 0 ; on name or range of money or category
        listProductDTO = new ArrayList<>();
        try {
            con = DBUtils.makeCon();

//            String sql = "select Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity from Product where Status=?";
            String sql = "select Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity "
                    + " from Product "
                    + " where Status=? AND Quantity > 0 AND Name LIKE ? "
                    + " order by CreateDate DESC";
            prs = con.prepareStatement(sql);
            prs.setString(1, "Active");
            prs.setString(2, "%" + Name + "%");
            rs = prs.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String image = rs.getString(2);
                String descrip = rs.getString(3);
                float price = rs.getFloat(4);
                String createDate = rs.getString(5);
                String categoryID = rs.getString(6);
                String status = rs.getString(7);
                int quantity = rs.getInt(8);
                listProductDTO.add(new ProductDTO(name, image, descrip, price, createDate, categoryID, status, quantity));
                if (flag == 0) {
                    hasResult = true;
                }
                flag++; // flag de chan khong phai set hasResult=true nhieu lan

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
        return hasResult;
    }

    public void getProducts() throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        listProductDTO = new ArrayList<>();
        try {
            con = DBUtils.makeCon();

            String sql = "select Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity from Product where Status=? ORDER BY CreateDate DESC";

            prs = con.prepareStatement(sql);
       
            prs.setString(1, "Active");

            rs = prs.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String image = rs.getString(2);
                String descrip = rs.getString(3);
                float price = rs.getFloat(4);
                String createDate = rs.getString(5);
                String categoryID = rs.getString(6);
                String status = rs.getString(7);
                int quantity = rs.getInt(8);
                listProductDTO.add(new ProductDTO(name, image, descrip, price, createDate, categoryID, status, quantity));

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
}
