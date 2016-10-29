/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author 20120093
 */
@WebServlet(name = "DispatcherServlet", urlPatterns = {"/DispatcherServlet"})
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.log("Beginn service()");
        resp.setContentType("text/html");
        
        //PrintWriter out = res.getWriter();
        //out.println("Aktuelles Datum: ");
        
        RequestDispatcher dis = req.getRequestDispatcher("/html/formularIncludeForward.html");
        
        try {
            dis.include(req, resp);
        } catch (Exception e) {
            this.log("Fehler: ", e);
            throw new ServletException("",e);
        }
        
        this.log("Ende service()");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.log("Beginn service()");
        resp.setContentType("text/html");
        
        //PrintWriter out = res.getWriter();
        //out.println("Aktuelles Datum: ");
        //resp.getOutputStream().println("Die Anfrage verwendet "+req.getParameter("type"));
        
        RequestDispatcher dis = req.getRequestDispatcher("/TimeServlet");
        if("committed".equals(req.getParameter("committed")))
            resp.flushBuffer();
        try {
            
            if("include".equals(req.getParameter("type")))
                dis.include(req, resp);
            else if("forward".equals(req.getParameter("type")))
                dis.forward(req, resp);
            else 
                throw new RuntimeException("illegal parameter");
            
        } catch (Exception e) {
            this.log("Fehler: ", e);
            RequestDispatcher disErr = req.getRequestDispatcher("/html/errorDispatcher.html");
            disErr.include(req, resp);
        }
        
        this.log("Ende service()");
    }

    
}
