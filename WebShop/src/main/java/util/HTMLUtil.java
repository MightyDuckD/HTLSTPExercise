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
//        builder.append("<div class=\"input-group\">");
//        builder.append("<input id=\"").append(id).append("\" class=\"input-group-lg\" type=\"text\" value=\"1\" name=\"").append(id).append("\">\n"
//                + "<button class=\"btn\" type=\"button\" value=\"Add to Warenkorb\">\n"
//                + "<script>\n"
//                + "    $(\"input[name='").append(id).append("']\").TouchSpin({\n"
////                + "      verticalbuttons: true,\n"
//                + "      prefix: \"Add \",\n"
//                + "      verticalupclass: 'glyphicon glyphicon-plus',\n"
//                + "      verticaldownclass: 'glyphicon glyphicon-minus'\n"
//                + "    });\n"
//                + "</script>");
//        builder.append("</div>");

        builder.append("<div class=\"input-group col-sm-12\">\n"
                + "            <input id=\"").append(id).append("\" type=\"text\" value=\"1\" name=\"").append(id).append("\" class=\"form-control\">\n"
                        + "<div class=\"input-group-btn\"><button type=\"button\" onclick=\"addToWarenkorb(").append(id).append(");\" class=\"btn btn-default\">Add to Warenkorb</button></div>"
                        + "        </div>\n"
                        + "        <script>\n"
                        + "            $(\"input[name='").append(id).append("']\").TouchSpin({\n"
                        //                        + "                prefix: \"\" ,"
                        //                        + "                postfix: \"Add to Warenkorb\",\n"
                        //                        + "                postfix_extraclass: \"btn btn-info\"\n"
                        + "            });\n"
                        + "        </script>");

        return builder.toString();//
    }

}
