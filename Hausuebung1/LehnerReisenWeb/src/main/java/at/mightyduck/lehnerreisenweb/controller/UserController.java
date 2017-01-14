/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lehnerreisenweb.controller;

import at.mightyduck.lehnerreisenconsole.model.Benutzer;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Simon
 */
@Named("usercon")
@SessionScoped
public class UserController implements Serializable {

    private Benutzer benutzer;

    public Benutzer getBenutzer() {
        return benutzer; 
    }

}
