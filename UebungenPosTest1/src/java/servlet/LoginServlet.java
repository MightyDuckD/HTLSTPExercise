/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Simon
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, null);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            request.getRequestDispatcher("/html/loginformular.html").include(request, response);
            out.println(message == null ? "" : message);
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String message[] = new String[]{"Falsche Username/Passwort Kombination"};
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        Arrays.stream(request.getServletContext().getInitParameter("user").split("\n"))
                .map(String::trim)
                .peek(System.out::println)
                .map(s -> s.split(":"))
                .filter(token -> token[0].equals(user) && token[1].equals(pass))
                .findAny()
                .ifPresent(login -> {
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        session.invalidate();
                    }
                    session = request.getSession();
                    session.setAttribute("user", login[0]);
                    try {
                        request.getRequestDispatcher("/WelcomeServlet").forward(request, response);
                    } catch (ServletException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

        processRequest(request, response, message[0]);
    }
}
