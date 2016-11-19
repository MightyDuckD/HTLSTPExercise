/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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

    @PostConstruct
    public void init() {
        person = new Person();
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

}
