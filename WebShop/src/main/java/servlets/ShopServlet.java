/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Artikel;
import model.Category;
import model.Prize;
import util.AttributeUtil;
import util.HTMLUtil;
import util.ResourceUtil;

/**
 *
 * @author Simon
 */
@WebServlet(name = "ShopServlet", urlPatterns = {"/Shop/*"})
public class ShopServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(request.getRequestURI());

        PrintWriter out = response.getWriter();
        DAO dao = (DAO) request.getServletContext().getAttribute(AttributeUtil.DAO);
        System.out.println("shop");
        out.println("<script src=\"/WebShop/js/warenkorb.js\"></script>");
        out.println("<style>.row.extra-bottom-padding{\n"
                + "   margin-bottom: 20px;\n"
                + "}</style>");

        out.println("<div class=\"col-lg-3\">");
        printNavbar(request, response);
        out.println("</div>");
        out.println("<div class=\"col-lg-9\">");
        printShop(request, response);
        out.println("</div>");

        out.println("<style>"
                + "a.list-group-item {color: #fff;}"
                + ""
                + ".just-padding {\n"
                + "  padding: 15px;\n"
                + "}\n"
                + "\n"
                + ".list-group.list-group-root {\n"
                + "  padding: 0;\n"
                + "  overflow: hidden;\n"
                + "}\n"
                + "\n"
                + ".list-group.list-group-root .list-group {\n"
                + "  margin-bottom: 0;\n"
                + "}\n"
                + "\n"
                + ".list-group.list-group-root .list-group-item {\n"
                + "  border-radius: 0;\n"
                + "  border-width: 1px 0 0 0;\n"
                + "}\n"
                + "\n"
                + ".list-group.list-group-root > .list-group-item:first-child {\n"
                + "  border-top-width: 0;\n"
                + "}\n"
                + "\n"
                + ".list-group.list-group-root > .list-group > .list-group-item {\n"
                + "  padding-left: 30px;\n"
                + "}\n"
                + "\n"
                + ".list-group.list-group-root > .list-group > .list-group > .list-group-item {\n"
                + "  padding-left: 45px;\n"
                + "}</style>");

        out.println("<script>");
        out.println("$(\".bootstrap-touchspin-postfix\").click(function(event) {alert(this);})");
        out.println("</script>");

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
        return "Shop";
    }

    private void printNavbar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        DAO dao = (DAO) request.getServletContext().getAttribute(AttributeUtil.DAO);
        out.println("<div class=\"list-group list-group-root well\">");
        String uri = request.getRequestURI();
        if (uri.endsWith("/Shop")) {
            uri += "/";
        }

        for (Category g : dao.getAllCategorien()) {
            if (g.getParent() == null) {
                List<Category> childs = dao.getChildrenById(g.getId());
                String extra = uri.endsWith("/Shop/" + g.getPath()) ? "selected" : "";
                for (Category k : childs) {
                    if (uri.endsWith("/Shop/" + k.getPath())) {
                        extra = "selected";
                    }
                }

                out.println("<a  href=\"/WebShop/Shop/" + g.getPath() + "\"  class=\"text-success list-group-item " + extra + "\">" + g.getBezeichnung() + "</a>");
                if (!"".equals(extra)) {
                    if (!childs.isEmpty()) {
                        out.println("<div class=\"list-group\">");
                        for (Category k : childs) {
                            String childextra = uri.endsWith("/Shop/" + k.getPath()) ? "selected" : "";
                            out.println("<a href=\"/WebShop/Shop/" + k.getPath() + "\" class=\"list-group-item " + childextra + "\">" + k.getBezeichnung() + "</a>");
                        }
                        out.println("</div>");
                    }
                }
            }
        }
        //        out.println(" <a href=\"#\" class=\"list-group-item\">Item 1.2</a>\n"
//                + "    <div class=\"list-group\">\n"
//                + "      <a href=\"#\" class=\"list-group-item\">Item 1.2.1</a>\n"
//                + "      <a href=\"#\" class=\"list-group-item\">Item 1.2.2</a>\n"
//                + "      <a href=\"#\" class=\"list-group-item\">Item 1.2.3</a>\n"
//                + "    </div>\n"
//                + "    ");
        out.println("</div>");
        out.println("<style>.selected {color: #00bc8c !important;}</style>");

    }

    private void printShop(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        DAO dao = (DAO) request.getServletContext().getAttribute(AttributeUtil.DAO);
        out.println("<style>.panel-extra-margin {margin-bottom: 10px!important;  }</style>");
        out.println("<div id=\"shop-content\" class=\"panel-group\">");

        int col = -1;
        int cols = 3;
        String path = request.getRequestURI();
        if(path.endsWith("/Shop"))
            path += "/";
        path = path.substring(path.indexOf("/Shop") + 6);
        Category k = dao.getKategorieByPath(path);
        for (Artikel a : dao.getAllArtikelByKategorie(k)) {
            col = (col + 1) % cols;
            Prize p = a.getCurrentPrize();
            out.println("<div class=\"col-sm-" + (12 / cols) + "\">");
            out.println("<div class=\"panel panel-extra-margin  panel-default\">");

            out.println("<div class=\"panel-heading\">");
            out.println(a.getBezeichnung());
            out.println("<span>" + p.getCent() / 100 + "." + p.getCent() % 100 + " -" + String.format("%.0f", p.getDiscount() * 100) + "%</span>");
            out.println("</div>");

            out.println("<div class=\"panel-body\">");
            out.println("<img style=\"width: 100%; height:100%;\" src=\"" + ResourceUtil.getImageResource(a.getImageLarge()) + "\"><br>");
            out.println("</div>");

            out.println("<div class=\"panel-footer\">");

            out.println("<div class=\"row\">");

            out.println(HTMLUtil.generateSpinner("" + a.getId()));
            out.println("</div>");

            out.println("</div>");

            out.println("</div>");
            out.println("</div>");
        }
        out.println("</div>");
    }
}
