/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loctp.HanaShop;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Loc
 */
public class Cart implements Serializable{
    private HashMap<String,OrderDTO> items;

    public HashMap<String, OrderDTO> getItems() {
        return items;
    }

    public void setItems(HashMap<String, OrderDTO> items) {
        this.items = items;
    }
    
    public Cart(){
        items= new HashMap<>();
    }
    public void AddtoCart(OrderDTO dto){
        if(items==null){
            items= new HashMap<>();
            
        }
        try{
        if(this.items.containsKey(dto.getProductName())){
            String productName= dto.getProductName();
            int quantity= this.items.get(productName).getQuantity()+1;
            this.items.get(productName).setQuantity(quantity);
            this.items.get(productName).setTotal(quantity*dto.getPrice());
        }else{
          this.items.put(dto.getProductName(), dto);  
        }
        }catch(Exception ex){
            System.out.println("Exception_AddtoCart: "+ex.getMessage());
        }
        
    }
    
  
    
    public void removefromCart(String foodName){
        if(this.items.containsKey(foodName)){
            this.items.remove(foodName);
        }
        // check if items empty? -> set it null
        if(this.items.isEmpty()){
            this.items=null;
        }
        
    }
    public void updateQuantity(String foodName,int quantity){
        if(this.items.containsKey(foodName)){
            //SET QUANTITY
            this.items.get(foodName).setQuantity(quantity);
            // SET TOTAL
            this.items.get(foodName).setTotal(quantity*this.items.get(foodName).getPrice());
        }
        //IF QUANTITY =0 -> REMOVE IT
        if(this.items.get(foodName).getQuantity() == 0){
            this.items.remove(foodName);
        }
        
    }
    
    
    public void clear(){
        this.items.clear();
    }
    
    
    
    
}
