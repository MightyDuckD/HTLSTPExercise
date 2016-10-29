/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author 20120093
 */
@WebServlet(name = "TimeServlet", urlPatterns = {"/TimeServlet"})
public class TimeServlet extends HttpServlet {

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        this.log("Beginn von service()");
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        DateFormat df = DateFormat.getDateInstance();
        String date = df.format(new Date());
        out.println("\n" +
"        <link rel=\"stylesheet\" href=\"/HalloWeb/css/default.css\"> ");
        out.println("<h1>"+date+"</h1>"
                + "");
        this.log("Ende von service()");
    }
}
