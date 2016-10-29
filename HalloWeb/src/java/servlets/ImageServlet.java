/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.LoggingUtil;

/**
 *
 * @author 20120093
 */
@WebServlet(name = "ImageServlet", urlPatterns = {"/ImageServlet"})
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dis = request.getRequestDispatcher("/html/formularImage.html");
        response.setContentType("text/html;charset=UTF-8");
        dis.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nr = request.getParameter("file");
        String pfad = this.getServletContext().getRealPath("/res/" + nr);
        File f = new File(pfad);
        String ext = pfad.substring(pfad.lastIndexOf("."));
        response.setContentType("image/" + ext);
        response.setContentLength((int) f.length());

        response.setHeader("Content-Disposition", "inline;filename=\"" + f.getName() + "\"");

        try (OutputStream os = response.getOutputStream();
            FileInputStream is = new FileInputStream(pfad)) {
            Files.copy(FileSystems.getDefault().getPath(pfad),os);
        } catch (IOException e) {
            this.log("IOFehler 1:", e);
        }
    }



@Override
        public String getServletInfo() {
        return "Short description";
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            super.init(config); //To change body of generated methods, choose Tools | Templates.
        } catch (Exception e) {
            LoggingUtil.log(config, this.getClass().getName() + ".init() threw execption " + e);
            throw e;
        }
        LoggingUtil.log(config, this.getClass().getName() + ".init() called");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        try {
            super.service(req, res);
        } catch (Exception e) {
            LoggingUtil.log(req, this.getClass().getName() + ".service() threw execption " + e);
            throw e;
        }
        LoggingUtil.log(req, this.getClass().getName() + ".service() called");
    }
}
