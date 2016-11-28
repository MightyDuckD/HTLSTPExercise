/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Simon
 */
@WebServlet(name = "FileServlet", urlPatterns = {"/FileServlet"})
public class FileServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>File Chooser</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>File Chooser</h1>");

        request.getRequestDispatcher("/html/fileformular.html").include(request, response);

        try {
            String file = request.getParameter("file");
            if (file != null) {
                String name = this.getServletContext().getRealPath("/files/" + file);
                File f = new File(name);
                out.println("<div> " +  file.replaceAll("[^a-zA-Z0-9\\/\\.\\-]", "") + " found on server! </div>");
            }
        } catch (NullPointerException ex) {
            throw new FileNotFoundException("404 - File not found.");
        }
        out.println("</body>");
        out.println("</html>");
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
