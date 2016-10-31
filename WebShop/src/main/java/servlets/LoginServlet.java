/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.dsig.DigestMethod;
import model.User;
import util.AttributeUtil;
import static util.AttributeUtil.DAO;
import util.TemplateUtil;
import util.UserUtil;
import static util.UserUtil.byteArrayToHex;

/**
 *
 * @author Simon
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/Login"})
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
//        writer.append((CharSequence) request.getSession().getAttribute("user"));

        String logout = request.getParameter("logout");
        if (logout != null) {
            request.getSession().removeAttribute(AttributeUtil.USER);
            request.getSession().setAttribute(AttributeUtil.LOGIN_MSG, "<span class=\"text-success\">Successfully logged out.</span>");
            response.sendRedirect("/WebShop/Login");
            return;
        }

//        if (message != null && !"".equals(message)) {
//            writer.append("<p>").append(message).append("</p>");
//        }
        String message = getCustomLoginMessage(request, response);
        if (request.getSession().getAttribute(AttributeUtil.USER) == null) {
            String[] parts = TemplateUtil.getTemplateParts(request.getServletContext(), "/html/formularLogin.html", "<!--MESSAGE-->");
            writer.append(parts[0]);
            if (message != null && !"".equals(message)) {
                writer.append(message);
            }
            writer.append(parts[1]);
        } else {
            writer.append("<h3>Login</h3>");
            writer.append("You are already logged in.");
            writer.append("If you are not ").append((CharSequence) request.getSession().getAttribute(AttributeUtil.USER)).append("<a href=\"Login?logout\"> logout here</a>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session.getAttribute(AttributeUtil.USER) == null) {
            String user = request.getParameter("username");
            String pass = request.getParameter("password");
            if (user != null && pass != null) {
                DAO dao = (DAO) request.getServletContext().getAttribute(AttributeUtil.DAO);
                User u = dao.getUserByUsername(user);

                //check if password matches the db password
                if (UserUtil.authenticateUser(u, user, pass)) {
                    //successfully logged in
                    String uri = (String) session.getAttribute(AttributeUtil.LOGIN_URI);
                    session.removeAttribute(AttributeUtil.LOGIN_URI);
                    session.setAttribute(AttributeUtil.USER, user);
                    response.sendRedirect(uri != null ? uri : "");
                } else {
                    //failed
                    request.getSession().setAttribute(AttributeUtil.LOGIN_MSG, "<span class=\"text-danger\">Username or Password not correct.</span>");
                }
            }
        }
        processRequest(request, response);
    }

    private String getCustomLoginMessage(HttpServletRequest request, HttpServletResponse response) {
        Object attr = request.getSession().getAttribute(AttributeUtil.LOGIN_URI);
        if (attr != null) {
            System.out.println("message " + attr);
            switch ((String) attr) {
                case "/WebShop/Warenkorb":
                    return "<span class=\"text-danger\">You have to be logged in to access the Warenkorb</span>";
            }
        }
        Object mess = request.getSession().getAttribute(AttributeUtil.LOGIN_MSG);
        if (mess != null) {
            request.getSession().removeAttribute(AttributeUtil.LOGIN_MSG);
            return (String) mess;
        }
        return null;
    }

}
