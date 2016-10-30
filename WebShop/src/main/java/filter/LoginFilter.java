/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
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
@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String publicServlets[] = {"/", "/LoginServlet", "/ArtikelServlet"};

        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginNotNeeded = false;
        for (String servlet : publicServlets) {
            loginNotNeeded |= request.getRequestURI().equals(request.getContextPath() + servlet);
        }
        if (loggedIn || loginNotNeeded) {
            chain.doFilter(request, response);
        } else {
            RequestDispatcher headerDispatcher = request.getRequestDispatcher("/LoginServlet");
            headerDispatcher.forward(req, res);
        }
    }

    @Override
    public void destroy() {

    }

}
