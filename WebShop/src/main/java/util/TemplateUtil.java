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

    public static String getTemplate(ServletContext context) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResourceAsStream(templateFile), "UTF-8"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        return builder.toString();
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
        String template = getTemplate(context);
        String[] partsA = template.split(head);
        if (partsA.length != 2) {
            throw new RuntimeException("error parsing template file " + templateFile);
        }
        String[] partsB = partsA[1].split(body);
        if (partsB.length != 2) {
            throw new RuntimeException("error parsing template file " + templateFile);
        }
        return new String[]{partsA[0], partsB[0], partsB[1]};
    }
}
