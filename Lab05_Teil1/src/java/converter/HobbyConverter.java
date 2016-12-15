/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import model.Hobby;

/**
 *
 * @author Simon
 */
@FacesConverter(forClass = model.Hobby.class)
public class HobbyConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if("".equals(string))
            return null;
        for (Hobby hobby : Hobby.getHobbies()) {
            if (hobby.getBezeichnung().equals(string)) {
                return hobby;
            }
        }
        
        String valid = Hobby.getHobbies().stream().map(Hobby::getBezeichnung).reduce((a, b) -> {
            return a + "," + b;
        }).get();
        throw new ConverterException(new FacesMessage("Hobby ungültig (" + valid + ")"));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return ((Hobby) o).getBezeichnung();
    }

}
