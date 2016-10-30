/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Simon
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher headerDispatcher = request.getRequestDispatcher("/HeaderServlet");
        headerDispatcher.include(request, response);
        response.getWriter().append((CharSequence) request.getSession().getAttribute("user"));

        if (request.getSession().getAttribute("user") == null) {
            RequestDispatcher loginDispatcher = request.getRequestDispatcher("/html/formularLogin.html");
            loginDispatcher.include(request, response);
        } else {
            PrintWriter writer = response.getWriter();
            writer.append("<h3>Login</h3>");
            writer.append("It seems you are already logged in.");

            writer.append("If you are not ").append((CharSequence) request.getSession().getAttribute("user")).append("<a href=\"LoginServlet?logout\"> logout here</a>");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String logout = request.getParameter("logout");
        if (logout != null) {
            request.getSession().removeAttribute("user");
            request.getSession().invalidate();
        }
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("user") == null) {
            String user = request.getParameter("user");
            String pass = request.getParameter("pass");
            if (user != null && pass != null) {
                //TODO: login
                request.getSession().setAttribute("user", user);
            }
        }
        processRequest(request, response);
    }

}
