/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.AttributeUtil;

/**
 *
 * @author Simon
 */
@WebFilter(servletNames = {"CheckoutServlet"})
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        if (session != null) {
            session.setMaxInactiveInterval(40);
        }
        boolean loggedIn = session != null && session.getAttribute(AttributeUtil.USER) != null;
        if (!loggedIn) {
            if (session != null) {
                //remember the requested page so that the site can redirect to it after an successfull login
                session.setAttribute(AttributeUtil.LOGIN_URI, request.getRequestURI());
            }
            response.sendRedirect("Login");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

}
