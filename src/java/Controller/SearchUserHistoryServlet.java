/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loctp.HanaShop.OrderDAO;

/**
 *
 * @author Loc
 */
public class SearchUserHistoryServlet extends HttpServlet {
    private final String HISTORY_P="history.jsp";
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
        String url=HISTORY_P;
        int userchoice= Integer.parseInt(request.getParameter("cbUserchoice"));
   
        try {
            HttpSession sess= request.getSession(false);
            String Email=(String) sess.getAttribute("Email");
            
            OrderDAO DAO= new OrderDAO();
                switch(userchoice){
                    case 1:
                        // take name from jsp page
                        String name= request.getParameter("txtProductName");
                        
                        // search
                        DAO.searchOrderByName(Email, name);
                        
                        // store in session
                        sess.setAttribute("HISTORY", DAO.getListDTO());
                        break;
                    case 2:
                        String buyDate= request.getParameter("txtDate");
                        
                        buyDate= Date.valueOf(buyDate).toString();
                        
                        DAO.searchOrderByDate(Email, buyDate);
                        
                        // store in session
                        sess.setAttribute("HISTORY", DAO.getListDTO());
                        break;
                }
                
                
            
        } catch (ClassNotFoundException ex) {
            log("ClassNotFoundException_SearchUserHistoryServlet: "+ex.getMessage());
            
        } catch (SQLException ex) {
            log("SQLException_SearchUserHistoryServlet: "+ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException_SearchUserHistoryServlet: "+ex.getMessage());
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
