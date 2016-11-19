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

/**
 *
 * @author Simon
 */
@FacesConverter(value = "geschlechtconverter", forClass = Boolean.class)
public class GeschlechtConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if(string == null)
            return null;
        switch(string) {
            case "":
                return null;
            case "m":
                return false;
            case "w":
                return true;
        }
        throw new ConverterException(new FacesMessage("Es git nur 2 Geschlechter: m und w"));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null)
            return "";
        if(((Boolean)o))
            return "w";
        return "m";
        
    }
    
}
