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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loctp.HanaShop.ProductDAO;
import loctp.HanaShop.ProductDTO;
import loctp.HanaShop.RecordingDAO;

/**
 *
 * @author Loc
 */
public class UpdateAdminServlet extends HttpServlet {

    private final int NUMBER_PRODUCT_A_PAGE = 4;
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
        
        
        String name = request.getParameter("txtName");
        String image = request.getParameter("txtImage");
        if(image.trim().length()==0){
            image= request.getParameter("txtOldImage");
        }
        String description = request.getParameter("txtDescription");
        float price = Float.parseFloat(request.getParameter("txtPrice"));
        String categoryID = request.getParameter("cbCategory");
        String status = request.getParameter("cbStatus");
        int quantity = Integer.parseInt(request.getParameter("txtQuantity"));

        try {
            HttpSession sess = request.getSession(false);

            // update

            ProductDAO ProductDAO = new ProductDAO();

            ProductDAO.updateProduct(name, image, description, price, categoryID, status, quantity);

            // record changing
            // Record the update date
            RecordingDAO RecordingDAO = new RecordingDAO();
            RecordingDAO.getRecords();
            String updateDate = LocalDateTime.now().toString();

            // take userEmail
            String userEmail = (String) sess.getAttribute("Email");

            // Then record update date
            RecordingDAO.addRecording(name, updateDate, "Update", userEmail);

            // Done RECORD
            // LOAD DATA
            // LOAD LIST PRODUCT FOR HOME PAGE
            ProductDAO prodDAO = new ProductDAO();
            // get all list
            prodDAO.getProducts();
            List<ProductDTO> listProduct = prodDAO.getListProductDTO();

            // save all list to session
            sess.setAttribute("ALLPRODUCT", listProduct);

            // chia list phu de hien thi moi trang
            // page load luc dau la 1 nen ta co cong thuc
            // BEGIN = (pageIndex - 1) * NUMBER_PRODUCT_A_PAGE;
            // END = pageIndex*NUMBER_PRODUCT_A_PAGE;
            if (listProduct.size() > NUMBER_PRODUCT_A_PAGE) {
                List<ProductDTO> BeginList = listProduct.subList(0, NUMBER_PRODUCT_A_PAGE);
                sess.setAttribute("EACHPAGEPRODUCT", BeginList);
            } else {
                sess.setAttribute("EACHPAGEPRODUCT", listProduct);
            }

            // tinh so page
            int TotalPage = 0;
            int TotalProducts = listProduct.size();
            if (TotalProducts % NUMBER_PRODUCT_A_PAGE == 0) {
                TotalPage = TotalProducts / NUMBER_PRODUCT_A_PAGE;
            } else {
                TotalPage = (TotalProducts / NUMBER_PRODUCT_A_PAGE) + 1;
            }

            sess.setAttribute("MAXPAGE", TotalPage);

//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet UpdateAdminServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet UpdateAdminServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
        } catch (ClassNotFoundException ex) {
            log("ClassNotFoundException_UpdateAdminServlet: "+ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException_UpdateAdminServlet: "+ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException_UpdateAdminServlet: "+ex.getMessage());
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
