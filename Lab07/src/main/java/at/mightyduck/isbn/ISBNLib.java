/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.isbn;

import at.mightyduck.isbn.ISBNException;
import at.mightyduck.isbn.ISBN;
import at.mightyduck.isbn.xml.EAN_UCC;
import at.mightyduck.isbn.xml.Group;
import at.mightyduck.isbn.xml.RangeMessage;
import at.mightyduck.isbn.xml.Rule;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

/**
 * https://www.isbn-international.org/sites/default/files/ISBN%20Manual%202012%20-corr.pdf
 *
 * @author Simon
 */
public class ISBNLib {

    private static ISBNLib instance;
    private RangeMessage lib;

    private ISBNLib() {
        try {
            JAXBContext jc = JAXBContext.newInstance(RangeMessage.class);
            Unmarshaller un = jc.createUnmarshaller();
            InputStream in = ISBNLib.class.getResourceAsStream("/ISBNRangeMessage.xml");
            lib = (RangeMessage) un.unmarshal(in);
        } catch (JAXBException ex) {
            Logger.getLogger(ISBNLib.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static synchronized ISBNLib getInstance() {
        return (instance == null) ? (instance = new ISBNLib()) : instance;
    }





    public static Rule find(List<Rule> rules, String isbn) {
        for (Rule r : rules) {
            if (r.matches(isbn)) {
                return r;
            }
        }
        return null;
    }

    public static Group find(List<Group> groups, ISBN isbn) {
        for (Group g : groups) {
            if (g.matches(isbn)) {
                return g;
            }
        }
        return null;
    }


    public ISBN clean(String input) {
        ISBN result = new ISBN();
        boolean calculateChecksum = false;

        //is the input valid
        String isbn = input.replace("-", "");
        if (!isbn.matches("\\d{12}[\\dXx]{1}")) {
            throw new ISBNException("Falsche Länge oder ungültige Zeichen");
        }
        //should checksum be calculated afterwards?
        if (isbn.endsWith("x") || isbn.endsWith("X")) {
            System.out.println("i should calc the checksum");
            isbn = isbn.replaceAll("[xX]", "0");
            calculateChecksum = true;
        }

        for (EAN_UCC ean : lib.getEan_ucc()) {
            if (isbn.startsWith(ean.getPrefix())) {

                //Set the Prefix
                result.setPrefix(ean.getPrefix());
                result.setRegistrationGroupAgency(ean.getAgency());
                isbn = isbn.substring(ean.getPrefix().length());

                //Find the registration group
                Rule r1 = find(ean.getRules(), isbn);
                if (r1 == null || r1.isInvalid()) {
                    throw new ISBNException("Unbekannte Registration Group");
                }

                //Set the registration group
                result.setRegistrationGroup(isbn.substring(0, r1.getLength()));
                isbn = isbn.substring(r1.getLength());

                //Find the rules of the registration group
                Group g = find(lib.getRegistrationGroups(), result);
                if (g == null) {
                    throw new ISBNException("Definition der Registration Group konnte nicht gefunden werden");
                }

                //Find the registrant
                Rule r2 = find(g.getRules(), isbn);
                if (r2 == null || r2.isInvalid()) {
                    throw new ISBNException("Unbekannter Registrant");
                }

                //Set the remaining parts
                result.setRegistrant(isbn.substring(0, r2.getLength()));
                result.setRegistrantAgency(g.getAgency());
                result.setPublication(isbn.substring(r2.getLength(), isbn.length() - 1));
                result.setCheckDigit(isbn.substring(isbn.length() - 1, isbn.length()));
                
                System.out.println("done with isbn cleaning");
                System.out.println(isbn);
                System.out.println(calculateChecksum);
                
                //Calculated the checksum if necessary
                if(calculateChecksum) {
                    result.setCheckDigit(result.calculateCheckDigit());
                }
                
                return result;
            }
        }
        throw new ISBNException("Unbekanntes EAN Prefix");
    }

}
