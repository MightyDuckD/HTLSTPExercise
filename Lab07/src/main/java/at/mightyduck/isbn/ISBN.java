/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.isbn;

/**
 *
 * @author Simon
 */
public class ISBN {
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
        return String.format("%s-%s-%s-%s-%s", prefix, registrationGroup, registrant, publication, checkDigit);
    }
    
}
