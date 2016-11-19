/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.PrintWriter;

/**
 *
 * @author Simon
 */
public class HTMLUtil {

    public static String generateSpinner(String id) {
        StringBuilder builder = new StringBuilder();
        builder.append("<form action=\"/WebShop/Warenkorb\" method=\"POST\" >"
                + ""
                + "<input type=\"hidden\" name=\"do\" value=\"add\">"
                + "<input type=\"hidden\" name=\"id\" value=\"" + id + "\">"
                + "");
        builder.append("<div class=\"input-group col-sm-12\">\n"
                + "            <input id=\"spinner").append(id).append("\" type=\"text\" value=\"1\" name=\"spinner").append(id).append("\" class=\"form-control\">\n"
                        + "<div class=\"input-group-btn\"><button type=\"submit\"  class=\"btn btn-default\">Add</button></div>"
                        + "        </div>\n"
                        + "        <script>\n"
                        + "            $(\"input[name='spinner").append(id).append("']\").TouchSpin({\n"
                        + "            });\n"
                        + "        </script>");
        builder.append("</form>");
        return builder.toString();//
    }

}
