/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Simon
 */
@FacesValidator(value = "datumvorheutevalidator")
public class DatumVorHeuteValidator implements Validator{

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if(new Date().before((Date) o))
            throw new ValidatorException(new FacesMessage("Datum darf nicht nach dem heutigen Datum liegen"));
    }
    
}
