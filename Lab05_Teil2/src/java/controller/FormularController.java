/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.DAO;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import model.Person;

/**
 *
 * @author Simon
 */
@Named(value = "formCon")
@SessionScoped
public class FormularController implements Serializable {

    private Person person;
    private List<SelectItem> hobbies;

    @Inject
    private DAO dao;

    @PostConstruct
    public void init() {
        hobbies = dao.getHobbies()
                .stream()
                .map(h -> new SelectItem(h, h.getBezeichnung()))
                .collect(Collectors.toList());
    }

    public String formular2() {
        person = new Person();
        return "formular2";
    }

    public Person getPerson() {
        return person;
    }

    public List<Person> getPersonen() {
        return dao.getPersonen();
    }

    public List<SelectItem> getHobbies() {
        return hobbies;
    }

    public String save() {
        if (!dao.savePerson(person)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User konnte nicht gespeichert werden, versuchen Sie es später nochmal."));
            return "formular2";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User erfolgreich gespeichert"));
        return "viewuser";
    }
}
