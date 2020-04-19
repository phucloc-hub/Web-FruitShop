/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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

/**
 *
 * @author Loc
 */
public class SearchAdminServlet extends HttpServlet {

    private final int NUMBER_PRODUCT_A_PAGE = 4;
    private final String HOME = "home.jsp";

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
        String CategoryName = request.getParameter("cbCategory");
        String Status = request.getParameter("cbStatus");

        try {
            HttpSession sess = request.getSession(false);
            if (sess != null) {
                // Call searchAdmin method

                ProductDAO ProductDAO = new ProductDAO();
                boolean hasResult = ProductDAO.searchByAdmin(CategoryName, Status);
                if (hasResult) {

                    List<ProductDTO> listProduct = ProductDAO.getListProductDTO();
                    sess.setAttribute("ALLPRODUCT", listProduct);

                    // set Default Status for EACH_PAGE_PRODUCT
                    // chia list phu de hien thi moi trang
                    // page load luc dau la 1 nen ta co cong thuc
                    // BEGIN = (pageIndex - 1) * NUMBER_PRODUCT_A_PAGE;
                    // END = pageIndex*NUMBER_PRODUCT_A_PAGE;
                    // 
                    // Case: ket qua search khong du de chia trang
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

                }// end of if hasResult
                else {
                    request.setAttribute("NORECORD", "NORECORD");

                }

            }

//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet SearchAdminServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet SearchAdminServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
        } catch (ClassNotFoundException ex) {
           log("ClassNotFoundException_SearchAdminServlet: "+ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException_SearchAdminServlet: "+ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException_SearchAdminServlet: "+ex.getMessage());
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
