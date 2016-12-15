/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import model.Hobby;
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

    @PostConstruct
    public void init() {
        person = new Person();
        hobbies = Hobby.getHobbies()
                .stream()
                .map(h -> new SelectItem(h, h.getBezeichnung()))
                .collect(Collectors.toList());
    }

    public String formular2() {
        init();
        return "formular2";
    }

    public String formular1() {
        init();
        return "formular1";
    }

    public Person getPerson() {
        return person;
    }

    public List<SelectItem> getHobbies() {
        return hobbies;
    }
}
