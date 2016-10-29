/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.LoggingUtil;

/**
 *
 * @author 20120093
 */
@WebServlet(name = "HalloServlet", urlPatterns = {"/HalloServlet"}, initParams = {
    @WebInitParam(name = "myname", value = "Simon")})
public class HalloServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HalloServlet</title>");
            out.println("<link rel=\"stylesheet\" href=\"css/default.css\">"); 
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"bs-component col-lg-12\">");
            out.println("<h1>Servlet HalloServlet at " + request.getContextPath() + "</h1>");
            out.println("<h2>Developed by " + this.getInitParameter("myname") + "</h2>");
            Enumeration<String> headerNames = request.getHeaderNames();
            
            out.println("<h3>Alle Headerdaten</h3>");
            out.println(buildTable(extractHeaderData(request)));
            
            out.println("<h3>Alle Parameter</h3>");
            out.println(buildTable(extractParameterData(request)));
            out.println("<a href=\".\">Zurück zur Startseite</a>");
            out.println("</div>");
            out.println("</body>");
            
            
            out.println("</html>");
        }
    }
    public String[][] extractParameterData(HttpServletRequest request) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"Name","Value"});
        request.getParameterMap().forEach((key,value)-> {
            data.add(new String[]{
                key,
                Arrays.toString(value)
            });
        });
        return data.toArray(new String[data.size()][]);
    }
    
    public String[][] extractHeaderData(HttpServletRequest request) {
        Enumeration<String> names = request.getHeaderNames();
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"Name","Value"});
        while(names.hasMoreElements()) {
            String name = names.nextElement();
            data.add(new String[] {
                name,
                request.getHeader(name)
            });
        }
        return data.toArray(new String[data.size()][]);
    }

    public String buildTable( String data[][]) {
        StringBuilder out = new StringBuilder();
        out.append("<table class=\"table table-striped table-hover \">");
        for (String val : data[0]) {
            out.append("<th>");
            out.append(val);
            out.append("</th>");
        }
        for (int i = 1; i < data.length; i++) {
            out.append("<tr>");
            for (String val : data[i]) {
                out.append("<td>");
                out.append(val);
                out.append("</td>");
            }
            out.append("</tr>");
        }
        out.append("</table>");
        return out.toString();
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
