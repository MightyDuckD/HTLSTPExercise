/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lab07.validator;

import at.mightyduck.lab07.ISBNLib;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author Simon
 */
@FacesValidator("isbnvalidator")
public class IsbnValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if (o == null) {
            return;
        }
        ISBNLib lib = ISBNLib.getInstance();
        try {
            ISBNLib.ISBN isbn = lib.clean(o.toString());
            if (!isbn.isValidCheckDigit()) {
                throw new ValidatorException(new FacesMessage("Ungültige ISBN - Falsche Checksumme"));
            }
        } catch (ISBNLib.ISBNException ex) {
            throw new ValidatorException(new FacesMessage(ex.getMessage()));
        }
    }
}
