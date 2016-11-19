/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.DispatcherType;
import util.TemplateUtil;

/**
 *
 * @author Simon
 */
@WebFilter(
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.ERROR},
        servletNames = {
            "About", "Contact", "Services", "Error", "Home",
            "AccountServlet", "LoginServlet", "ShopServlet", "WarenkorbServlet", "CheckoutServlet"
        }
)
public class TemplateFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
        String[] template = TemplateUtil.getTemplateParts(request.getServletContext());
        PrintWriter writer = response.getWriter();
        writer.append(template[TemplateUtil.PRE]);
        writer.append(template[TemplateUtil.MID]);
        request.getRequestDispatcher("/Header").include(request, response);
        writer.append("<div class=\"container\">");
        fc.doFilter(request, response);
        writer.append("</div>");
        writer.append(template[TemplateUtil.POST]);

    }

    @Override
    public void destroy() {

    }

}
