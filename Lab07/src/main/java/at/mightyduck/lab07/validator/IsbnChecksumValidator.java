/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lab07.validator;

import at.mightyduck.lab07.ISBNLib;
import at.mightyduck.lab07.ISBNLib.Group;
import at.mightyduck.lab07.ISBNLib.Rule;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.xml.bind.JAXBException;
import org.apache.commons.validator.routines.ISBNValidator;
import org.apache.commons.validator.routines.checkdigit.ISBNCheckDigit;

/**
 * @author Simon
 */
@FacesValidator("isbnvalidator")
public class IsbnChecksumValidator implements Validator {

    private ISBNLib.ISBNRangeMessage lib;

    public IsbnChecksumValidator() {
        try {
            InputStream in = IsbnChecksumValidator.class.getResourceAsStream("/ISBNRangeMessage.xml");
            this.lib = ISBNLib.loadFromStream(in);
        } catch (JAXBException ex) {
            Logger.getLogger(IsbnChecksumValidator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if (o == null) {
            return;
        }
        
        //General validation by apache, both checksum and format
        //if (!ISBNValidator.getInstance().isValidISBN13(o.toString())) {
        //    throw new ValidatorException(new FacesMessage("Ungültige ISBN"));
        //}
    }
}
