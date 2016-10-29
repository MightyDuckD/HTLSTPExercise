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
        writer.append("<table>");
        List<Action> actions = dao.getAllActions();
        actions.sort(Action.COMPARE_BY_TIMESTAMP);
        int input = 10;
        try {
            input = Integer.parseInt(request.getParameter("count"));
        } catch(NumberFormatException ex ) {
            //no log
        }
        int max = Math.max(0,Math.min(actions.size(),input));
        for(Action a : dao.getAllActionsSorted(max)) {
            writer.append("<tr>");
            writer.append("<td>").append(Integer.toString(a.getId())).append("</td>");
            writer.append("<td>").append(Timestamp.calendarFormatter1.format(a.getCreateDate())).append("</td>");
            writer.append("<td>").append(a.getText()).append("</td>");
            writer.append("</tr>");
        }
        writer.append("</table>");
        
    }

    @Override
    public String getServletInfo() {
        return "Zeigt die Action Tabelle an";
    }

}
