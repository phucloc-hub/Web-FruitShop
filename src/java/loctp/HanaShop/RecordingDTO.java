/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loctp.HanaShop;

import java.io.Serializable;

/**
 *
 * @author Loc
 */
public class RecordingDTO implements Serializable{
    
    private String foodName;
    private String updateDate;
    private String options;
    private String owner;

    public RecordingDTO() {
    }

    public RecordingDTO(String foodName, String updateDate, String options, String owner) {
        this.foodName = foodName;
        this.updateDate = updateDate;
        this.options = options;
        this.owner = owner;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

 
  

 
    
    
    
}
