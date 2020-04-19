/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loctp.HanaShop.AccountDAO;
import loctp.HanaShop.AccountDTO;

/**
 *
 * @author Loc
 */
public class LoginGoogleServlet extends HttpServlet {
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
        String url=HOME;
        String email = request.getParameter("Email");
        System.out.println("email: "+email);
        String name = request.getParameter("Name");
        String password = "";
        try {
            HttpSession sess = request.getSession(false);

            // add new account
            AccountDAO DAO = new AccountDAO();
            // get  EistAccount
            AccountDTO dto = DAO.getExistAccount(email);

            if (dto == null) {// create new 
                DAO.addAccount(email, name, "1", "Member");
                sess.setAttribute("USERKIND", "Member");
                password = "1";
            } else {
                sess.setAttribute("USERKIND", dto.getRole().trim());
                
                password = dto.getPassword();
            }
            
            sess.setAttribute("FULLNAME", name);
            sess.setAttribute("Email", email);

            Cookie cookie = new Cookie("email", email);
            cookie.setMaxAge(60 * 3);// ton tai 60*3= 3 phut
            response.addCookie(cookie);
            cookie = new Cookie("password", password);
            cookie.setMaxAge(60 * 3);// ton tai 60*3= 3 phut
            response.addCookie(cookie);

        } catch (SQLException ex) {
            log("SQLException_LoginGoogleServlet: "+ex.getMessage());
        } catch (ClassNotFoundException ex) {
            log("ClassNotFoundException_LoginGoogleServlet: "+ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException_LoginGoogleServlet: "+ex.getMessage());
        } finally {
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
