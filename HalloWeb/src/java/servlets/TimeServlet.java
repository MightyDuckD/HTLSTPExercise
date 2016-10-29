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
import util.LoggingUtil;

/**
 *
 * @author 20120093
 */
@WebServlet(name = "TimeServlet", urlPatterns = {"/TimeServlet"})
public class TimeServlet extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        try {
            this.log("Beginn von service()");
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            DateFormat df = DateFormat.getDateInstance();
            String date = df.format(new Date());
            out.println("<link rel=\"stylesheet\" href=\"/HalloWeb/css/default.css\"> ");
            out.println("<h1>" + date + "</h1>");
            out.println("<a href=\".\">Zurück zur Startseite</a>");
            this.log("Ende von service()");
        } catch (Exception e) {
            LoggingUtil.log(req, this.getClass().getName() + ".service() threw execption " + e);
            throw e;
        }
        LoggingUtil.log(req, this.getClass().getName() + ".service() called");
    }

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

}
