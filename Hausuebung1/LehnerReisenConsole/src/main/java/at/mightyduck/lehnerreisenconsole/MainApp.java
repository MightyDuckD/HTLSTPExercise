/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lehnerreisenconsole;

import at.mightyduck.lehnerreisenconsole.model.Benutzer;
import at.mightyduck.lehnerreisenconsole.model.Reisetyp;
import at.mightyduck.lehnerreisenconsole.model.Reiseveranstaltung;
import at.mightyduck.lehnerreisenconsole.util.HibernateUtil;
import at.mightyduck.lehnerreisenconsole.util.UserUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 *
 * @author Simon
 */
public class MainApp {

    public static final Map<String, BiConsumer<String, BufferedReader>> commands = new HashMap<>();
    public static final DAO dao = new DAO();
    public static final Random random = new Random(System.currentTimeMillis());

    static {
        commands.put("list", MainApp::listData);
        commands.put("help", MainApp::printHelp);
    }
    
    

    public static void main(String[] args) throws IOException {
        System.out.println("Opening hibernate session ...");
        HibernateUtil.getSessionFactory();
        HibernateUtil.drop();
        HibernateUtil.create();

        System.out.println("Filling database with data ...");
        Benutzer[] users = new Benutzer[]{
            UserUtil.createUser("simon@mightyduck.at", "password"),
            UserUtil.createUser("lehner@mightyduck.at", "password")
        };

        Reisetyp[] typen = new Reisetyp[]{
            new Reisetyp(null, "Städtereisen"),
            new Reisetyp(null, "Italien"),
            new Reisetyp(null, "Last Minute")
        };

        //Reisen von hofer-reisen.at
        Reiseveranstaltung[] veranstalungen = new Reiseveranstaltung[]{
            new Reiseveranstaltung(
            null, "Wien",
            "Hotel Ibis Wien Mariahilf \nFrühstück\n2 Nächte\ninkl. 3-Gang-Menü im Restaurant Kardos!",
            "http://www.hofer-reisen.at/reiseangebote/oesterreich/wien/wien/wien-hotel-ibis-wien-mariahilf-9062110",
            new GregorianCalendar(2017, Calendar.JANUARY, 12).getTime(), null,
            79.00,typen[0],"wien1.jpg"
            ),
            new Reiseveranstaltung(
            null, "Wien",
            "JUFA Hotel Wien City\nFrühstück\n2 Nächte",
            "http://www.hofer-reisen.at/reiseangebote/oesterreich/wien/wien/wien-jufa-hotel-wien-city-9040566",
            new GregorianCalendar(2017, Calendar.JANUARY, 12).getTime(), null,
            59.00,typen[2],"wien2.jpg"
            ),
            new Reiseveranstaltung(
            null, "Venedig",
            "Frühstück\n2 Nächte",
            "",
            new GregorianCalendar(2017, Calendar.JANUARY, 12).getTime(), null,
            59.00,typen[1],"venedig1.jpg"
            ),
            new Reiseveranstaltung(
            null, "Wien",
            "JUFA Hotel Wien Erlebnistage",
            "http://www.hofer-reisen.at/reiseangebote/oesterreich/wien/wien/wien-jufa-hotel-wien-city-9040566",
            new GregorianCalendar(2017, Calendar.JANUARY, 12).getTime(), null,
            59.00,typen[2],"wien3.jpg"
            )
        };
        
        users[0].getInteressen().add(typen[0]);
        users[0].getInteressen().add(typen[1]);
        

        Arrays.stream(typen).forEach(dao::save);
        Arrays.stream(users).forEach(dao::save);
        Arrays.stream(veranstalungen).forEach(dao::save);

        try {
            if (args.length != 1 || (!"--noconsole".equals(args[0]) && !"-n".equals(args[0]))) {
//                commandLine();
            }
        } finally {
            System.out.println("Shutting down hibernate ...");
            HibernateUtil.close();
        }
    }

    public static void commandLine() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        System.out.print(">");
        while ((line = reader.readLine()) != null) {
            String parts[] = line.split(" ");
            if ("exit".equals(parts[0])) {
                break;
            }
            commands.getOrDefault(parts[0], (input, stream) -> {
                System.out.println("command not found, try 'help'");
            }).accept(line, reader);
            System.out.print(">");
        }
    }

    public static void listData(String line, BufferedReader stream) {
        String[] parts = line.split(" ");
        if (parts.length == 1) {
            System.out.println("need 1 argument [user|typ|event]");
        } else {
            List data = null;
            switch (parts[1]) {
                case "user":
                    data = dao.getBenutzer();
                    break;
                case "typ":
                    data = dao.getReisetypen();
                    break;
                case "event":
                    data = dao.getReiseveranstaltungen();
                    break;
            }
            if (data == null) {
                System.out.println("illegal paramter");
            } else {
                System.out.println(data.stream().reduce((a, b) -> a + "\n" + b).orElse("no data found"));
            }
        }
    }

    public static void printHelp(String line, BufferedReader stream) {
        System.out.println("Lehner Reisen Console App");
        System.out.println("by Simon Lehner-Dittenberger");
        System.out.println("Call with argument --noconsole or -n to not go into console input mode.");
        System.out.println("All commands: ");
        for (String command : commands.keySet()) {
            System.out.println(command);
        }
    }
}
