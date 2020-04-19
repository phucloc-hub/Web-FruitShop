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
public class OrderDTO implements Serializable{
    private String email;
    private String productName;
    private int quantity;
    private float total;
    private String buyDate;
    private float price;

    public OrderDTO() {
    }

    public OrderDTO(String email, String productName, int quantity, float total, String buyDate, float price) {
        this.email = email;
        this.productName = productName;
        this.quantity = quantity;
        this.total = total;
        this.buyDate = buyDate;
        this.price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    
    
    
}
