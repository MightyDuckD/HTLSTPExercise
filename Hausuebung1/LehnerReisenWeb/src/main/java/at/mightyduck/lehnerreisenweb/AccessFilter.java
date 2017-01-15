/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lehnerreisenweb;

import at.mightyduck.lehnerreisenweb.controller.UserController;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
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

/**
 *
 * @author Simon
 */
@WebFilter(urlPatterns = "/member/*")
@Named
public class AccessFilter implements Filter {
    
    
    @Inject
    private UserController usercon; 


    @Override
    public void init(FilterConfig fc) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        
        if (usercon != null && usercon.getBenutzer() != null) {
            fc.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/hello.xhtml#signup");
        }
    }

    @Override
    public void destroy() {

    }

}
