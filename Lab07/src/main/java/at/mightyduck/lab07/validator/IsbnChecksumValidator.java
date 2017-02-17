/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lab07.validator;

import java.util.Scanner;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.commons.validator.ISBNValidator;

/**
 * @author Simon
 * https://www.isbn-international.org/sites/default/files/ISBN%20Manual%202012%20-corr.pdf
 */
@FacesValidator("isbnvalidator")
public class IsbnChecksumValidator implements Validator {

    private ISBNValidator validator = new ISBNValidator();
    
    public void validateChecksum(String input) throws ValidatorException {
        String isbn = input.replace("-", "");
        int checksum = 0;
        for (int i = 0; i < 12; i++) {
            checksum += (i % 2 == 0 ? 1 : 3) * (isbn.charAt(i) - '0');
        }
        int checkdigit = 10 - (checksum % 10);
        if (checkdigit == 10) {
            checkdigit = 0;
        }
        if (checkdigit != isbn.charAt(12) - '0') {
            throw new ValidatorException(new FacesMessage("Checksumme nicht korrekt"));
        }
    }

    public void validateFormat(String input) throws ValidatorException {
        String isbn = input.replace("-", "");
        if (!isbn.matches("\\d{13}")) {
            throw new ValidatorException(new FacesMessage("Ungültige Länge oder illegale Zeichen"));
        }
        if (!validator.isValid(isbn)) {
            throw new ValidatorException(new FacesMessage("Ungültige ISBN"));
        }
//        if (!isbn.startsWith("978")) {
//            throw new ValidatorException(new FacesMessage("Nicht unterstütztes Prefix"));
//        }
//        int regGroupLength = registrationGroupLength(isbn);

    }

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if (o == null) {
            return;
        }
        validateFormat(o.toString());
        validateChecksum(o.toString());
    }

//    /**
//     * see Table 2: Method of deriving the registration group structure for
//     * prefix element 978
//     *
//     * @param isbn
//     * @return The length of the Registration Group
//     */
//    private int registrationGroupLength(String isbn) {
//        String group = isbn.substring(3, 3 + 5);
//        int groupval = Integer.parseInt(group);
//        final int[] rangeUpper = new int[]{60000, 65000, 70000, 80000, 95000, 99000, 99900, 100000};
//        final int[] size = new int[]{1, 3, 0, 1, 2, 3, 4, 5};
//        for (int i = 0; i < rangeUpper.length; i++) {
//            //0 is undefined
//            if (groupval < rangeUpper[i] && size[i] != 0) {
//                return size[i];
//            }
//        }
//        throw new FacesException("Registration Group is undefined");
//    }
}
