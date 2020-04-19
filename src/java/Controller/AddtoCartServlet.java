/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author Loc
 */
public class AddtoCartServlet extends HttpServlet {
    private final String HOME="home.jsp";
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
        String url= HOME;
        String foodName= request.getParameter("foodName");
        System.out.println("foodName: "+foodName);
        float price= Float.parseFloat(request.getParameter("price"));
        System.out.println("price: "+price);
        
        int quantity=1;
        float total= price*quantity;
        String buyDate= LocalDateTime.now().toString();
        String email = "";
        
        try {
            HttpSession sess= request.getSession(false);

            if(sess!= null){

                email= (String)sess.getAttribute("Email");
                if(email!= null){
                
                // Get cart
                Cart shoppingCart= (Cart)sess.getAttribute("CART");
                if(shoppingCart==null){
                    shoppingCart=new Cart();
                }

                shoppingCart.AddtoCart(new OrderDTO(email, foodName, quantity, total, buyDate, price));
                sess.setAttribute("CART", shoppingCart);
                }
                
            }
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
