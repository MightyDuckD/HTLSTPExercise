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
import org.apache.commons.validator.routines.ISBNValidator;
import org.apache.commons.validator.routines.checkdigit.ISBNCheckDigit;

/**
 * @author Simon
 */
@FacesValidator("isbnvalidator")
public class IsbnChecksumValidator implements Validator {


    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if (o == null) {
            return;
        }
        //General validation by apache, both checksum and format
        if (!ISBNValidator.getInstance().isValidISBN13(o.toString())) {
            throw new ValidatorException(new FacesMessage("Ungültige ISBN"));
        }
    }
}
