/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loctp.HanaShop.Cart;
import loctp.HanaShop.OrderDAO;
import loctp.HanaShop.OrderDTO;
import loctp.HanaShop.ProductDAO;

/**
 *
 * @author Loc
 */
public class CheckOutServlet extends HttpServlet {
    private final String CART_P="viewCart.jsp";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url=CART_P;
        boolean IsEnough=true;
        String ErrorName="";
        String email="",productName="",buyDate="";
        int quantity=0;
        float total=0,price=0;
        try {
                HttpSession sess= request.getSession(false);
                if(sess!= null){
                    Cart shoppingCart=(Cart)sess.getAttribute("CART");
                    if(shoppingCart != null){
                    HashMap<String,OrderDTO> items= shoppingCart.getItems();
                    OrderDAO DAO= new OrderDAO();
                    ProductDAO ProductDAO= new ProductDAO();
                    for(String key : items.keySet()){
                        quantity= items.get(key).getQuantity();
                        int CurrentQuantity= ProductDAO.getQuantity(items.get(key).getProductName());
                        if(CurrentQuantity<quantity){
                            ErrorName= items.get(key).getProductName();
                            IsEnough=false;
                            break;
                        }
                        
                    }
                    if(IsEnough==true){
                    for (String key : items.keySet()) {
                        
                        email= items.get(key).getEmail();
                        productName=items.get(key).getProductName();
                        quantity= items.get(key).getQuantity();
                        total= items.get(key).getTotal();
                        price= items.get(key).getPrice();
                        buyDate= items.get(key).getBuyDate();
                        
                        //UPDATE PRODUCT table first
                        
                        
                        ProductDAO.updateQuantity(productName, quantity);
                        
                        // Insert into database
                        DAO.insertOrder(email, productName, quantity, total, buyDate, price,"Cash");
                        
                    }
                    shoppingCart.clear();
                    sess.setAttribute("CART", shoppingCart);
                    request.setAttribute("Thanks", "thanks");
                    }else{
                        request.setAttribute("ERROR", "Not enough, less than it! 1 : "+ErrorName);
                        
                    }
                    }  
                }



        } catch (ClassNotFoundException ex) {
            log("ClassNotFoundException_CheckOutServlet: "+ex.getMessage());
        } catch (SQLException ex) {
            
            log("SQLException_CheckOutServlet:  " + ex.getMessage());
                    if (ex.getMessage().contains("Quantity_must_positive")) {
  
                        request.setAttribute("ERROR", "Not enough, less than it! : "+productName);
                    }
        } catch (NamingException ex) {
            log("NamingException_CheckOutServlet: "+ex.getMessage());
        }finally{
            RequestDispatcher rd= request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
