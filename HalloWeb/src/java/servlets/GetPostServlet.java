/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.LoggingUtil;

/**
 *
 * @author 20120093
 */
@WebServlet(name = "GetPostServlet", urlPatterns = {"/GetPostServlet"})
public class GetPostServlet extends HttpServlet {

    protected void reportType(String typ, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GetPostServlet</title>");
            out.println("<link rel=\"stylesheet\" href=\"css/default.css\">");       
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetPostServlet</h1>");
            out.println("<h2>Abfragetype: " + typ + "</h2>");
            out.println("<a href=\"/HalloWeb/html/testGetPost.html\"> go back </a>");
            out.println("</body>");
            out.println("</html>");
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
        reportType("GET", response);
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
        reportType("POST", response);
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

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            super.init(config); //To change body of generated methods, choose Tools | Templates.
        } catch (Exception e) {
            LoggingUtil.log(config, this.getClass().getName() + ".init() threw execption " + e);
            throw e;
        }
        LoggingUtil.log(config, this.getClass().getName() + ".init() called");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        try {
            super.service(req, res);
        } catch (Exception e) {
            LoggingUtil.log(req, this.getClass().getName() + ".service() threw execption " + e);
            throw e;
        }
        LoggingUtil.log(req, this.getClass().getName() + ".service() called");
    }
}
