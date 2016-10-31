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
import util.AttributeUtil;

/**
 *
 * @author Simon
 */
@WebServlet(name = "HeaderServlet", urlPatterns = {"/Header"})
public class HeaderServlet extends HttpServlet {

    private static enum HeaderItem {

        HOME("Home", "/WebShop/", true, true,""),
        ABOUT("About", "/WebShop/About", true, true,""),
        SERVICES("Services", "/WebShop/Services", true, true,""),
        ARTIKEL("Shop", "/WebShop/Shop", true, true,""),
        WARENKORB("Warenkorb", "/WebShop/Warenkorb", true, true,""),
        CONTACT("Contact", "/WebShop/Contact", true, true,""),
        LOGGEDIN("Login", "/WebShop/Login", false, true,""),
        LOGGEDOUT("Account", "/WebShop/Account", true, false,"");

        public final String name, path;
        private final String aAttributes;
        public final boolean loggedIn, loggedOut;

        private HeaderItem(String name, String path, boolean loggedIn, boolean loggedOut,String aAttributes) {
            this.name = name;
            this.path = path;
            this.loggedIn = loggedIn;
            this.loggedOut = loggedOut;
            this.aAttributes = aAttributes;
        }

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean loggedIn = request.getSession().getAttribute(AttributeUtil.USER) != null;
        PrintWriter out = response.getWriter();
        out.println("<header>");
        out.println("<nav class=\"navbar navbar-default\">");
        out.println("<div class=\"container\">");
        out.println("<div class=\"navbar-header\"><a class=\"navbar-brand\" href=\"/WebShop\">WebShop</a></div>");
        out.println("<ul class=\"nav navbar-nav navbar-right\">");
        for (HeaderItem item : HeaderItem.values()) {
            if ((loggedIn && item.loggedIn) || (!loggedIn && item.loggedOut)) {
                boolean active = item.path.equals(request.getRequestURI());
                out.append("<li").append(active ? " class=\"active\"" : "").append(">");
                out.append("<a ").append(item.aAttributes).append(" href=\"").append(item.path).append("\">");
                out.append(item.name);
                out.append("</a>");
                out.append("</li>");
            }
        }
        out.println("</ul>");
        out.println("</div>");
        out.println("</nav>");
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
