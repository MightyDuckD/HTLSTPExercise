/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import db.DAO;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import model.Hobby;

/**
 *
 * @author Simon
 */
@FacesConverter("hobbyconverter")
public class HobbyConverter implements Converter {

    @Inject
    private DAO dao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if ("".equals(string)) {
            return null;
        }
        for (Hobby hobby : dao.getHobbies()) {
            if (hobby.getBezeichnung().equals(string)) {
                return hobby;
            }
        }

        String valid = dao.getHobbies().stream().map(Hobby::getBezeichnung).reduce((a, b) -> {
            return a + "," + b;
        }).get();
        throw new ConverterException(new FacesMessage("Hobby ungültig (" + valid + ")"));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return ((Hobby) o).getBezeichnung();
    }

}
