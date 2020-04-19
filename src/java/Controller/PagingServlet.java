/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loctp.HanaShop.ProductDTO;

/**
 *
 * @author Loc
 */
public class PagingServlet extends HttpServlet {
    private final String HOME_P="home.jsp";
    private final int NUMBER_PRODUCT_A_PAGE=4;
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
        String url= HOME_P;
        int PageIndex=  Integer.parseInt(request.getParameter("PageIndex"));
        
        try{
            HttpSession sess= request.getSession(false);// sess must be existed!
            
            // CALL ALLLIST FROM SESSION
            List<ProductDTO> ALLPRODUCT = (List<ProductDTO>) sess.getAttribute("ALLPRODUCT");
            
            // tinh khoang de tach tu TOTAL LIST
            int BEGIN= (PageIndex-1)*NUMBER_PRODUCT_A_PAGE;
            int END= PageIndex*NUMBER_PRODUCT_A_PAGE;
            
            // if END > TOTAL_LIST.size() -> SET AGAINT
            if(END> ALLPRODUCT.size()){
                END=ALLPRODUCT.size();
            }
            
            
            List<ProductDTO> EACHPAGEPRODUCT = ALLPRODUCT.subList(BEGIN, END);
            
            // UPDATE EACH_PAGE_PRODUCT  
            sess.setAttribute("EACHPAGEPRODUCT", EACHPAGEPRODUCT);
            
            
            
            
            
            
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet PagingServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet PagingServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
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
