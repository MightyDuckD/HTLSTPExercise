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
import util.TemplateUtil;

/**
 *
 * @author Simon
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] template = TemplateUtil.getTemplateParts(request.getServletContext());
        PrintWriter writer = response.getWriter();
        writer.append(template[TemplateUtil.PRE]);
        writer.append(template[TemplateUtil.MID]);
        request.getRequestDispatcher("/HeaderServlet").include(request, response);
        //PRE
        writer.append((CharSequence) request.getSession().getAttribute("user"));
        String logout = request.getParameter("logout");
        if (logout != null) {
            request.getSession().removeAttribute("user");
            request.getSession().invalidate();
            writer.append("You are now logged out");
        }

        if (request.getSession().getAttribute("user") == null) {
            RequestDispatcher loginDispatcher = request.getRequestDispatcher("/html/formularLogin.html");
            loginDispatcher.include(request, response);
        } else {
            writer.append("<h3>Login</h3>");
            writer.append("It seems you are already logged in.");
            writer.append("If you are not ").append((CharSequence) request.getSession().getAttribute("user")).append("<a href=\"LoginServlet?logout\"> logout here</a>");
        }
        //POST
        writer.append(template[TemplateUtil.POST]);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
                String uri = (String) request.getAttribute("javax.servlet.forward.request_uri");
                request.getSession().setAttribute("user", user);
                response.sendRedirect(uri != null ? uri : "");
            }
        }
        processRequest(request, response);
    }

}
