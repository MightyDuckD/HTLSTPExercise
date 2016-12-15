/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 *
 * @author Simon
 */
@WebFilter(servletNames = {"TestServlet","SprachenServlet"})
public class TemplateFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        PrintWriter writer = sr1.getWriter();
        String[] template = loadTemplate(sr.getServletContext().getResourceAsStream("/html/template.html"));
        writer.append(template[0]);
        fc.doFilter(sr, sr1);
        writer.append(template[1]);
    }

    @Override
    public void destroy() {
    }

    private String[] loadTemplate(InputStream resourceAsStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        byte[] buffer = new byte[1024 * 4];
        int len;
        while ((len = resourceAsStream.read(buffer)) != -1) {
            builder.append(new String(buffer, 0, len));
        }
        return builder.toString().split("<!--CONTENT-->");
    }

}
