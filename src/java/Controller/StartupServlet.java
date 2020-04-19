/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loctp.HanaShop.AccountDAO;
import loctp.HanaShop.ProductDAO;
import loctp.HanaShop.ProductDTO;

/**
 *
 * @author Loc
 */
public class StartupServlet extends HttpServlet {

    private final String SHOW_P = "home.jsp";
    private final int NUMBER_PRODUCT_A_PAGE = 4;

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
        String userEmail = "", password = "";
        String url = SHOW_P;
        try {

            Cookie[] cookies = request.getCookies();
            //2.Check cookie
            HttpSession sess = request.getSession(true);

            if (cookies != null) {
                System.out.println("Cookie exist!");
                //3. de get username va password
                for (Cookie cooky : cookies) {
                    if (cooky.getName().equals("email")) {
                        userEmail = cooky.getValue();
                    }
                    if (cooky.getName().equals("password")) {
                        password = cooky.getValue();
                    }

                    AccountDAO dao = new AccountDAO();
                    String result = dao.checkLogin(userEmail, password);//
                    int point = result.indexOf(':');
                    if (!result.isEmpty()) {
                        String username = result.substring(0, point).trim();
                        String Role = result.substring(point + 1).trim();
                        sess.setAttribute("Email", userEmail);
                        sess.setAttribute("USERKIND", Role.trim());
                        sess.setAttribute("FULLNAME", username);
                       

//                        url = SEARCH_PAGE;
                        break;

                    }

                }

            }

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
//            List<ProductDTO> BeginList= listProduct.subList(0, NUMBER_PRODUCT_A_PAGE);
//            sess.setAttribute("EACHPAGEPRODUCT", BeginList);
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

            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet StartupServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet StartupServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
        } catch (SQLException ex) {
            log("SQLException_StartupServlet : " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            log("ClssNotFoundException_StartupServlet : " + ex.getMessage());
        } catch (NamingException ex) {
            log("NamingException_StartupServlet : " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
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
