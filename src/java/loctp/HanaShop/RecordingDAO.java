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
import loctp.Utils.DBUtils;

/**
 *
 * @author Loc
 */
public class RecordingDAO {

    List<RecordingDTO> listRecordingDTO;

    public List<RecordingDTO> getListRecordingDTO() {
        return listRecordingDTO;
    }

    public void getRecords() throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        listRecordingDTO = new ArrayList<>();
        try {
            con = DBUtils.makeCon();

            String sql = "select RecordID,FoodName,UpdateDate,Options,Owner from Recording";

            prs = con.prepareStatement(sql);

            rs = prs.executeQuery();
            while (rs.next()) {

                String foodName = rs.getString(1);
                String updateDate = rs.getString(2);
                String options = rs.getString(3);
                String owner = rs.getString(4);
                listRecordingDTO.add(new RecordingDTO(foodName, updateDate, options, owner));

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

    public void addRecording(String foodName, String updateDate, String options, String owner) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement prs = null;
        
        try {
            con = DBUtils.makeCon();

            String sql = "Insert into Recording (FoodName,UpdateDate,Options,Owner) values(?,?,?,?);";
            prs = con.prepareStatement(sql);
            prs.setString(1, foodName);
            prs.setString(2, updateDate);
            prs.setString(3, options);
            prs.setString(4, owner);

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
