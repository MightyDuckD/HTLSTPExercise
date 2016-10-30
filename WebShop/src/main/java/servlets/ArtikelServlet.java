/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.TemplateUtil;

/**
 *
 * @author Simon
 */
@WebServlet(name = "ArtikelServlet", urlPatterns = {"/ArtikelServlet"})
public class ArtikelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] template = TemplateUtil.getTemplateParts(request.getServletContext());
        PrintWriter writer = response.getWriter();
        writer.append(template[TemplateUtil.PRE]);
        writer.append(template[TemplateUtil.MID]);
        request.getRequestDispatcher("/HeaderServlet").include(request, response);
        //PRE
        writer.append("Hallo auf der Haupseite");
        //POST
        writer.append(template[TemplateUtil.POST]);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] template = TemplateUtil.getTemplateParts(request.getServletContext());
        PrintWriter writer = response.getWriter();
        writer.append(template[TemplateUtil.PRE]);
        writer.append(template[TemplateUtil.MID]);
        request.getRequestDispatcher("/HeaderServlet").include(request, response);
        //PRE
        writer.append("Hallo auf der Haupseite");
        //POST
        writer.append(template[TemplateUtil.POST]);
    }

    @Override
    public String getServletInfo() {
        return "Artikel";
    }
}
