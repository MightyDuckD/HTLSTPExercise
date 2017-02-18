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
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Simon
 */
@Named("isbnlib")
@ApplicationScoped
public class ISBNLib {

    private static JAXBContext jc;
    private static Unmarshaller unmarshaller;

    static {
        try {
            jc = JAXBContext.newInstance(ISBNRangeMessage.class);
            unmarshaller = jc.createUnmarshaller();
        } catch (JAXBException ex) {
            Logger.getLogger(ISBNLib.class.getName()).log(Level.SEVERE, null, ex);
        }
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
         * True if the first digits of the given isbn is inside the range of this rule.
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

    }

    public void test() {
        try {
            JAXBContext jc = JAXBContext.newInstance(ISBNRangeMessage.class);

            Unmarshaller unmarshaller = jc.createUnmarshaller();
            InputStream in = ISBNLib.class.getResourceAsStream("/ISBNRangeMessage.xml");
            if (in == null) {
                System.out.println(" is null ");
            }
            ISBNRangeMessage msg = (ISBNRangeMessage) unmarshaller.unmarshal(in);

            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(msg, System.out);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static ISBNRangeMessage loadFromStream(InputStream in) throws JAXBException {
        return (ISBNRangeMessage) unmarshaller.unmarshal(in);
    }
    
    private static Rule find(List<Rule> rules, String isbn) {
        for (Rule r : rules) {
            if (r.matches(isbn)) {
                return r;
            }
        }
        return null;
    }
    public static String clean (ISBNRangeMessage lib, String input) {
        String isbn = input.replace("-", "");
        String clean = "";
        Rule r1 = null, r2 = null;
        if (!isbn.matches("\\d{13}")) {
            throw new ValidatorException(new FacesMessage("Ungültige ISBN"));
        }
        System.out.println("called ?");
        for (ISBNLib.EAN_UCC ean : lib.getEan_ucc()) {
            if (isbn.startsWith(ean.getPrefix())) {
                clean += ean.getPrefix();
                System.out.println("EAN.UCC:Prefix = " + ean.getPrefix());
                System.out.println("EAN.UCC:Agency = " + ean.getAgency());
                isbn = isbn.substring(3);

                r1 = find(ean.getRules(), isbn);
                if (r1 == null || r1.isInvalid()) {
                    throw new ValidatorException(new FacesMessage("Ungültige ISBN - invalid registration group"));
                }

                System.out.println("r1 found");
                clean += "-" + isbn.substring(0, r1.getLength());
                isbn = isbn.substring(r1.getLength());

                for (Group g : lib.getRegistrationGroups()) {
                    if (clean.equals(g.getPrefix())) {
                        System.out.println("Group:Prefix = " + g.getPrefix());
                        System.out.println("Group:Agency = " + g.getAgency());
                        r2 = find(g.getRules(), isbn);
                        if (r2 == null || r2.isInvalid()) {
                            throw new ValidatorException(new FacesMessage("Ungültige ISBN - invalid registrant"));
                        }
                        clean += "-" + isbn.substring(0, r2.getLength());
                        isbn = isbn.substring(r2.getLength());
                        clean += "-" + isbn;
                        isbn = clean;
                        break;
                    }
                }
                break;
            }
        }
        System.out.println("r1 " + r1.getRange() + " " + r1.getLength());
        System.out.println("r2 " + r2.getRange() + " " + r2.getLength());
        System.out.println("final cleaned version " + isbn);
        return clean;
    }
}
