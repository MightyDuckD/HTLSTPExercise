/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lehnerreisenweb;

import at.mightyduck.lehnerreisenconsole.DAO;
import at.mightyduck.lehnerreisenconsole.model.Reisetyp;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Simon
 */
@Named("reisetypconverter")
@ApplicationScoped
public class ReisetypConverter implements Converter {

    @Inject
    private DAO dao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        System.out.println("convert to object " + string);
        for (Reisetyp typ : dao.getReisetypen()) {
            if (typ.getBezeichnung().equals(string)) {
                return typ;
            }
        }
        throw new ConverterException();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        System.out.println("convert to string " + o.getClass() + " " + o);
        return ((Reisetyp) o).getBezeichnung();
    }

}
