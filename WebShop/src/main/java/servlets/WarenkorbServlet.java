/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;
import model.Warenkorb;
import util.AttributeUtil;
import util.TemplateUtil;
import util.WarenkorbUtil;

/**
 *
 * @author Simon
 */
@WebServlet(name = "WarenkorbServlet", urlPatterns = {"/Warenkorb"})
public class WarenkorbServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        out.print("Warenkorb is saved in " + WarenkorbUtil.getWarenkorbSource(request) + "<br>");
        Warenkorb warenkorb = WarenkorbUtil.getWarenkorb(request);
        out.println("Created on " + warenkorb.getCreated() + "<br>");

        out.println("<div class=\"panel panel-default\" >");

        out.println("<div class=\"panel-heading\">Warenkorb</div>");

//        out.println("<div class=\"panel-body\">");
        out.println("<table class=\"table\" style=\"margin-bottom: 0px;\">");
        out.println("<tr>");
        out.println("<th class=\"col-sm-1 text-right\">Quantity</th>");
        out.println("<th>Artikel</th>");
        out.println("<th class=\"col-sm-1 text-right\">Unit</th>");
        out.println("<th class=\"col-sm-1 text-right\">Total</th>");
        out.println("<th class=\"col-sm-1 text-right\"></th>");
        int total = 0;
        out.println("</tr>");
        for (Warenkorb.WarenkorbItem item : warenkorb.getItems()) {
            printItem(out, item);
            int cent = item.getArtikel().getPrize().getCent();
            total += cent * item.getAmount();
        }
        out.println("</table>");
//        out.println("</div>");
        out.println("<div class=\"panel-footer\" style=\"\n"
                + "    padding-left: 0px;\n"
                + "    padding-right: 0px;\n"
                + "\">");
        out.println("<div class=\"row\">");
        out.println("<div class=\"col-sm-2 col-sm-offset-9  text-right\">");
        out.println(String.format("%.2f&euro;", total / 100.0));
        out.println("</div>");
        out.println("</div>");

        out.println("</div>");
        out.println("</div>");

        out.println("<a href=\"/WebShop/Shop\" class=\"col-sm-2 btn btn-default\">Back to the Shop</a>");
        out.println("<a href=\"/WebShop/Checkout\" class=\"col-sm-2 col-sm-offset-8 btn btn-success\">Go to Checkout</a>");

//        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
//            System.out.println(entry.getKey() + " " + Arrays.toString(entry.getValue()));
//        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DAO dao = (DAO) request.getServletContext().getAttribute(AttributeUtil.DAO);
        Warenkorb warenkorb = WarenkorbUtil.getWarenkorb(request);
        String doit = request.getParameter("do");

        if (doit != null) {
            switch (doit) {
                case "add": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    int amount = Integer.parseInt(request.getParameter("spinner" + id));
                    warenkorb.addItem(new Warenkorb.WarenkorbItem((int) (Math.random() * 100000), amount, dao.getArtikelById(id)));//TODO: id generation
                    break;
                }
                case "remove": {
                    int id = Integer.parseInt(request.getParameter("wid"));
                    warenkorb.removeItemById(id);
                    break;
                }
            }
        }

        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void printItem(PrintWriter out, Warenkorb.WarenkorbItem item) {
        out.println("<tr>");
        out.println("<td class=\"col-sm-1 text-right\">");
        out.println(item.getAmount());
        out.println("</td>");

        out.println("<td>");
        out.println(item.getArtikel().getName());
        out.println("</td>");

        int cent = item.getArtikel().getPrize().getCent();
        out.println("<td class=\"col-sm-1 text-right\">");
        out.println(String.format("%.2f&euro;", cent / 100.0));
        out.println("</td>");

        out.println("<td class=\"col-sm-1 text-right\">");
        out.println(String.format("%.2f&euro;", item.getAmount() * cent / 100.0));
        out.println("</td>");

        out.println("<td class=\"col-sm-1 text-right\">");
        out.println("<div class=\"col-sm-1\">"
                + "<form action=\"/WebShop/Warenkorb\" method=\"POST\">"
                + "<input type=\"hidden\" name=\"wid\" value=\"" + item.getId() + "\">"
                + "<input type=\"hidden\" name=\"do\" value=\"remove\">"
                + "<button type=\"submit\" class=\"btn btn-xs btn-danger\">"
                + "<span class=\"glyphicon glyphicon-remove-circle\" aria-hidden=\"true\"></span>"
                + "</button>"
                + "</form>"
                + "</div>");
        out.println("</td>");

        out.println("</tr>");
    }

}
