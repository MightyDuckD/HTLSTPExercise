/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Simon
 */
@WebServlet(name = "ChooserServlet", urlPatterns = {"/ChooserServlet"})
public class ChooserServlet extends HttpServlet {

    private final static Map<String, String> outputs;

    static {
        outputs = new HashMap<>();
        outputs.put("Bike", "Ich habe ein Fahrrad.");
        outputs.put("Car", "Ich habe ein Auto.");
        outputs.put("NoVehicle", "Ich habe kein Fahrzeug.");
        outputs.put("female", "Ich bin eine Frau.");
        outputs.put("male", "Ich bin ein Mann.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet ChooserServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>ChooserServlet</h1>");
        request.getRequestDispatcher("/html/chooserformular.html").include(request, response);
        out.println("</body>");
        out.println("</html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet ChooserServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>ChooserServlet</h1>");
        try {
            String[] sex = request.getParameterValues("sex");
            String[] vehicle = request.getParameterValues("vehicle");
            Stream.of(sex, vehicle != null ? vehicle : new String[]{"NoVehicle"})
                    .filter(Objects::nonNull)
                    .flatMap(Arrays::stream)
                    .map(s -> outputs.getOrDefault(s, "Unkown value"))
                    .map(s -> "<div>" + s + "</div>")
                    .forEach(out::println);
        } catch (NullPointerException ex) {
            response.sendError(response.SC_BAD_REQUEST);
        }
        out.println("</body>");
        out.println("</html>");
    }

}
