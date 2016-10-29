/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.sun.xml.ws.security.opt.impl.tokens.Timestamp;
import dao.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Action;

/**
 *
 * @author 20120093
 */
@WebServlet(name = "ShowActionServlet", urlPatterns = {"/ShowActionServlet"})
public class ShowActionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dis = request.getRequestDispatcher("/html/formularAction.html");
        response.setContentType("text/html;charset=UTF-8");
        dis.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO dao = (DAO) request.getServletContext().getAttribute("dao");
        PrintWriter writer = response.getWriter();
        if (request.getParameter("dropall") != null) {
            if (!dao.dropAll()) {
                response.sendError(500);
            }
            return;
        }
        int input = 10;
        try {
            input = Integer.parseInt(request.getParameter("count"));
        } catch (NumberFormatException ex) {
            //no log/no error
        }
        int max = Math.max(0, input);
        List<Action> actions = dao.getAllActionsSorted(max);
        if (actions == null) {
            response.sendError(500);
            return;
        }
        for (Action a : actions) {
            writer.append("<tr class=\"" + a.getDecoration() + "\">");
            writer.append("<td>").append(Integer.toString(a.getId())).append("</td>");
            writer.append("<td>").append(Timestamp.calendarFormatter1.format(a.getCreateDate())).append("</td>");
            writer.append("<td>").append(a.getText()).append("</td>");
            writer.append("</tr>");
        }

    }

    @Override
    public String getServletInfo() {
        return "Zeigt die Action Tabelle an";
    }

}
