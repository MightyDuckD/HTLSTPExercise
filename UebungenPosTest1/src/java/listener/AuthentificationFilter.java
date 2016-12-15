/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Simon
 */
@WebFilter()
public class AuthentificationFilter implements Filter {

    private ServletContext ctx;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ctx = filterConfig.getServletContext();
        ctx.log("AutentificationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        System.out.println(uri);
        
        HttpSession session = req.getSession(false);
        ctx.log("-------------------------------------------------------------------");
        ctx.log("AuthentificationFilter:");
        if (session == null && !(uri.equals("/UebungenPosTest1/") || uri.endsWith("LoginServlet"))) {
            ctx.log("Unauthorized access request");
            RequestDispatcher rd = ctx.getRequestDispatcher("/LoginServlet");
            rd.forward(request, response);
        } else {
            // pass the request along the filter chain
            ctx.log("Authorized access request");
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // clean up
    }
}
