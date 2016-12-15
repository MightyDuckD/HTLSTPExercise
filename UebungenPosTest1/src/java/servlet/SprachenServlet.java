/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Simon
 */
@WebServlet(name = "SprachenServlet", urlPatterns = {"/SprachenServlet"}, initParams = {
    @WebInitParam(name = "number", value = "true")
})
public class SprachenServlet extends HttpServlet {

    public static enum Sprache {

        JAVA("Java"),
        CSHARP("CSharp"),
        CPLUSPLUS("C++"),
        PASCAL("Pascal");
        private final String text;

        private Sprache(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
        
        public static Sprache getByOrdinal(int ordinal) {
            return values()[ordinal];
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/html/sprachen.html").include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String[] params = request.getParameterValues("languages");
        if (params == null || params.length == 0) {
            throw new SprachenException();
        } else {
            if ("true".equals(getInitParameter("number"))) {
                out.println(params.length);
            } else {
                Arrays.stream(params)
                        .map(Integer::parseInt)
                        .map(Sprache::getByOrdinal)
                        .map(Sprache::getText)
                        .reduce((a, b) -> a + " " + b)
                        .ifPresent(out::println);
            }
        }
    }

    public static class SprachenException extends RuntimeException {

        public SprachenException() {

        }

        public SprachenException(String message) {
            super(message);
        }

    }

}
