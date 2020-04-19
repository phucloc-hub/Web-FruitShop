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

/**
 *
 * @author Loc
 */
public class AddAdminServlet extends HttpServlet {

    private final int NUMBER_PRODUCT_A_PAGE = 4;
    private final String HOME = "home.jsp";
    private final String ERROR_P="addProduct.jsp";

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
        String url = HOME;
        String name = request.getParameter("txtName").trim();
        String image = request.getParameter("txtImage").trim();
        String description = request.getParameter("txtDescription").trim();
        float price = Float.parseFloat(request.getParameter("txtPrice"));
        String categoryID = request.getParameter("cbCategory").trim();
        int quantity = Integer.parseInt(request.getParameter("txtQuantity"));
        String CreateDate = LocalDateTime.now().toString();
        String Status = "Active";

        //select Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity 
        try {

            ProductDAO ProductDAO = new ProductDAO();
            if (!ProductDAO.isExistProduct(name)) {

                ProductDAO.addProduct(name, image, description, price, CreateDate, categoryID, Status, quantity);

                HttpSession sess = request.getSession(false);
                ProductDAO.getProducts();
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

//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet AddAdminServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet AddAdminServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
            }else{
                request.setAttribute("ExistName", "ExistName");
                url=ERROR_P;
            }
        } catch (ClassNotFoundException ex) {
            log("ClassNotFoundException_AddAdminServlet: " + ex.getMessage());
        } catch (SQLException ex) {
            log("SQLException_AddAdminServlet: " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException_AddAdminServlet: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
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
