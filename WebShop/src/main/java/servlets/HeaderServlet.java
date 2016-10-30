/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Simon
 */
@WebServlet(name = "HeaderServlet", urlPatterns = {"/HeaderServlet"})
public class HeaderServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<header>");
        out.println("<h1>WebShop</h1>");
        out.println("<nav><ul>");
        out.println("<li><a href=\"/WebShop/\">Home</a></li>");
        if (request.getSession().getAttribute("user") != null) {
            out.println("<li> <a href=\"/WebShop/LoginServlet?logout\">Logout</a></li>");
        } else {
            out.println("<li> <a href=\"/WebShop/LoginServlet\">Login</a></li>");
        }
        out.println("</ul></nav>");
        out.println("</header>");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
