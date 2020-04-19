/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loctp.Utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Loc
 */
public class DBUtils implements Serializable{
      public static Connection makeCon()
            throws ClassNotFoundException, SQLException, NamingException
    {
        
//        
//        //1.Load Driver
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        
//        
//        //2.
//        String dbName="TEST";
//        String url="jdbc:sqlserver://localhost:1433;databaseName="+dbName+";instanceName=SQLEXPRESS";
//        
//        
//        //3.
//        Connection con = DriverManager.getConnection(url,"SE130101","se130101");
//        
//        return con;
//        
////      
        
        //1. get current context(he dieu hanh cua may minh)
        Context context= new InitialContext();
        //2. take context in server side dua tren ten mat danh cua tomcat
        Context tomcat=(Context) context.lookup("java:comp/env");
        //3. get data source
        DataSource ds= (DataSource)tomcat.lookup("Lab2");
        //4. get connection
        
        Connection con= ds.getConnection();
     
                return con;
   
    }
    
}
