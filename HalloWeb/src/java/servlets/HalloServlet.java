/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            out.println("<style>table {\n"
                    + "    border-collapse: collapse;\n"
                    + "}\n"
                    + "\n"
                    + "table, th, td {\n"
                    + "    border: 1px solid black;\n"
                    + "}</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HalloServlet at " + request.getContextPath() + "</h1>");
            out.println("<h2>Developed by " + this.getInitParameter("myname") + "</h2>");
            Enumeration<String> headerNames = request.getHeaderNames();
            out.println("<h3>Alle Headerdaten</h3>");
            out.print("<table><tr><th>Name</th><th>Value</th></tr>");
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                out.append("<tr>");
                out.append("<td>").append(name).append("</td>");
                out.append("<td>").append(request.getHeader(name)).append("</td>");
                out.append("</tr>");
            }
            out.print("</table>");
            out.println("<h3>Alle Parameter</h3>");
            out.print("<table><tr><th>Name</th><th>Value</th></tr>");
            request.getParameterMap().forEach((name, value) -> {
                out.append("<tr>");
                out.append("<td>").append(name).append("</td>");
                out.append("<td>");
                out.append(Arrays.toString(value));
                out.append("</td>");
                out.append("</tr>");

            });
            out.print("</table>");
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
