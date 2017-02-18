/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lab07;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Simon
 */
public class ISBNLib {

    private static ISBNLib instance;
    private ISBNLib.ISBNRangeMessage lib;

    private ISBNLib() {
        try {
            JAXBContext jc = JAXBContext.newInstance(ISBNRangeMessage.class);
            Unmarshaller un = jc.createUnmarshaller();
            InputStream in = ISBNLib.class.getResourceAsStream("/ISBNRangeMessage.xml");
            lib = (ISBNRangeMessage) un.unmarshal(in);
        } catch (JAXBException ex) {
            Logger.getLogger(ISBNLib.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static synchronized ISBNLib getInstance() {
        return (instance == null) ? (instance = new ISBNLib()) : instance;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType
    @XmlRootElement(name = "ISBNRangeMessage")
    public static class ISBNRangeMessage {

        @XmlElement(name = "MessageSource", type = String.class, required = true)
        private String messageSource;
        @XmlElement(name = "MessageSerialNumber", type = String.class, required = true)
        private String messageSerialNumber;
        @XmlElement(name = "MessageDate", type = String.class, required = true)
        private String messageDate;

        @XmlElementWrapper(name = "EAN.UCCPrefixes")
        @XmlElement(name = "EAN.UCC")
        private List<EAN_UCC> ean_ucc;

        @XmlElementWrapper(name = "RegistrationGroups")
        @XmlElement(name = "Group")
        private List<Group> registrationGroups;

        public ISBNRangeMessage() {
        }

        public String getMessageSerialNumber() {
            return messageSerialNumber;
        }

        public void setMessageSerialNumber(String messageSerialNumber) {
            this.messageSerialNumber = messageSerialNumber;
        }

        public String getMessageDate() {
            return messageDate;
        }

        public void setMessageDate(String messageDate) {
            this.messageDate = messageDate;
        }

        public List<EAN_UCC> getEan_ucc() {
            return ean_ucc;
        }

        public void setEan_ucc(List<EAN_UCC> ean_ucc) {
            this.ean_ucc = ean_ucc;
        }

        public List<Group> getRegistrationGroups() {
            return registrationGroups;
        }

        public void setRegistrationGroups(List<Group> registrationGroups) {
            this.registrationGroups = registrationGroups;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType
    public static class EAN_UCC {

        @XmlElement(name = "Prefix", type = String.class, required = true)
        private String prefix;
        @XmlElement(name = "Agency", type = String.class, required = true)
        private String agency;
        @XmlElementWrapper(name = "Rules")
        @XmlElement(name = "Rule")
        private List<Rule> rules;

        public EAN_UCC() {
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getAgency() {
            return agency;
        }

        public void setAgency(String agency) {
            this.agency = agency;
        }

        public List<Rule> getRules() {
            return rules;
        }

        public void setRules(List<Rule> rules) {
            this.rules = rules;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType
    public static class Rule {

        @XmlElement(name = "Range", type = String.class, required = true)
        private String range;
        @XmlElement(name = "Length", type = Integer.class, required = true)
        private Integer length;

        public Rule() {
        }

        public String getRange() {
            return range;
        }

        public void setRange(String range) {
            System.out.println("set range " + range);
            this.range = range;
        }

        public Integer getLength() {
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        /**
         * True if the first digits of the given isbn is inside the range of
         * this rule.
         *
         * @param str
         * @throws IllegalStateException if the range of the rule is invalid
         * @throws NumberFormatException if the range of the rule is invalid
         * @return //TODO: Testcases
         */
        public boolean matches(String str) {
            String[] ranges = range.split("-");
            String lower = ranges[0];
            String upper = ranges[1];
            if (lower.length() != upper.length()) {
                throw new IllegalStateException("Rule is invalid");
            }
            int part = Integer.parseInt(str.substring(0, lower.length()));
            return Integer.parseInt(lower) <= part && part <= Integer.parseInt(upper);
        }

        /**
         * A Rule is invalid if length equals 0
         *
         * @return
         */
        public boolean isInvalid() {
            return getLength() == 0;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType
    public static class Group {

        @XmlElement(name = "Prefix", type = String.class, required = true)
        private String prefix;
        @XmlElement(name = "Agency", type = String.class, required = true)
        private String agency;
        @XmlElementWrapper(name = "Rules")
        @XmlElement(name = "Rule")
        private List<Rule> rules;

        public Group() {
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getAgency() {
            return agency;
        }

        public void setAgency(String agency) {
            this.agency = agency;
        }

        public List<Rule> getRules() {
            return rules;
        }

        public void setRules(List<Rule> rules) {
            this.rules = rules;
        }

        public boolean matches(ISBN result) {
            return this.getPrefix().equals(result.getPrefix() + "-" + result.getRegistrationGroup());
        }

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

    public static class ISBN {

        private String registrationGroupAgency;
        private String prefix;
        private String registrationGroup;
        private String registrant;
        private String publication;
        private String checkDigit;
        private String registrantAgency;

        public ISBN() {
        }

        public ISBN(String registrationGroupAgency, String prefix, String registrationGroup, String registrant, String publication, String checkDigit, String registrantAgency) {
            this.registrationGroupAgency = registrationGroupAgency;
            this.prefix = prefix;
            this.registrationGroup = registrationGroup;
            this.registrant = registrant;
            this.publication = publication;
            this.checkDigit = checkDigit;
            this.registrantAgency = registrantAgency;
        }

        public String getRegistrationGroupAgency() {
            return registrationGroupAgency;
        }

        public String getPrefix() {
            return prefix;
        }

        public String getRegistrationGroup() {
            return registrationGroup;
        }

        public String getRegistrant() {
            return registrant;
        }

        public String getPublication() {
            return publication;
        }

        public String getCheckDigit() {
            return checkDigit;
        }

        public String getRegistrantAgency() {
            return registrantAgency;
        }

        public void setRegistrationGroupAgency(String registrationGroupAgency) {
            this.registrationGroupAgency = registrationGroupAgency;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public void setRegistrationGroup(String registrationGroup) {
            this.registrationGroup = registrationGroup;
        }

        public void setRegistrant(String registrant) {
            this.registrant = registrant;
        }

        public void setPublication(String publication) {
            this.publication = publication;
        }

        public void setCheckDigit(String checkDigit) {
            this.checkDigit = checkDigit;
        }

        public void setRegistrantAgency(String registrantAgency) {
            this.registrantAgency = registrantAgency;
        }

        public String calculateCheckDigit() {
            String isbn = toString().replace("-", "");
            System.out.println(isbn);
            int checksum = 0;
            for (int i = 0; i < 12; i++) {
                checksum += (i % 2 == 0 ? 1 : 3) * (isbn.charAt(i) - '0');
            }
            int checkdigit = 10 - (checksum % 10);
            if (checkdigit == 10) {
                checkdigit = 0;
            }
            return "" + checkdigit;
        }

        public boolean isValidCheckDigit() {
            return calculateCheckDigit().equals(getCheckDigit());
        }

        @Override
        public String toString() {
            return String.format("%s-%s-%s-%s-%s",
                    prefix,
                    registrationGroup,
                    registrant,
                    publication,
                    checkDigit
            );
        }
    }

    public ISBN clean(String input) {
        ISBN result = new ISBN();
        String isbn = input.replace("-", "");
        if (!isbn.matches("\\d{13}")) {
            throw new ISBNException("Falsche Länge oder ungültige Zeichen");
        }
        for (ISBNLib.EAN_UCC ean : lib.getEan_ucc()) {
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
                break;
            }
        }
        return result;
    }

    public static class ISBNException extends RuntimeException {

        public ISBNException() {
        }

        public ISBNException(String message) {
            super(message);
        }

        public ISBNException(Throwable cause) {
            super(cause);
        }

        public ISBNException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
