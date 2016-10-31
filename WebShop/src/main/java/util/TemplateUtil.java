/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

/**
 *
 * @author Simon
 */
public class TemplateUtil {

    public static final int PRE = 0;
    public static final int MID = 1;
    public static final int POST = 2;

    public static final String body = "<!--CONTENT-->";
    public static final String head = "<!--HEADER-->";
    public static final String templateFile = "/html/template.html";

    public static String getTemplate(ServletContext context,String file) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResourceAsStream(file), "UTF-8"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        return builder.toString();
    }
    /**
     * Zerlegt ein Template file in die einzelnen Teile. Jede mark darf nur einmal vorkommen und muss eindeutig sein.
     * Die marks müssen in der gleichen Reihenfolge übergeben werden wie sie in der Datei vorkommen.
     * @param context
     * @param file
     * @param marks
     * @return
     * @throws IOException 
     */
    public static String[] getTemplateParts(ServletContext context, String file, String ... marks) throws IOException {
        String template = getTemplate(context,file);
        String result[] = new String[marks.length + 1];
        for(int i = 0; i<  marks.length; i++) {
            String[] sp = template.split(marks[i]);
            if(sp.length != 2)
                throw new RuntimeException("invalid template file " + file);
            result[i] = sp[0];
            template = sp[1];
        }
        result[marks.length] = template;
        return result;
    }

    /**
     * String array with the 3 sections of the template file (pre,between,post).
     * Example usage:
     * <pre>
     * {@code
     * PrintWriter writer = ....;
     * String temp[] = getTemplateParts();
     * writer.append(temp[PRE]);
     * writer.append("<title>Example</title>");
     * writer.append(temp[MID]);
     * writer.append("<div>Hallo Welt!</div>");
     * writer.append(temp[POST]);
     * }
     * </pre>
     *
     * @return The template file split in 3 parts: Before the head insert point,
     * between the head and the body insert point, after the body insert point.
     * @throws IOException
     */
    public static String[] getTemplateParts(ServletContext context) throws IOException {
//        String template = getTemplate(context);
//        String[] partsA = template.split(head);
//        if (partsA.length != 2) {
//            throw new RuntimeException("error parsing template file " + templateFile);
//        }
//        String[] partsB = partsA[1].split(body);
//        if (partsB.length != 2) {
//            throw new RuntimeException("error parsing template file " + templateFile);
//        }
//        return new String[]{partsA[0], partsB[0], partsB[1]};
        return getTemplateParts(context, templateFile, new String[]{head,body});
    }
}
