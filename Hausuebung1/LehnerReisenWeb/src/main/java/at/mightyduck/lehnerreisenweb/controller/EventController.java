/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lehnerreisenweb.controller;

import at.mightyduck.lehnerreisenconsole.DAO;
import at.mightyduck.lehnerreisenconsole.model.Reisetyp;
import at.mightyduck.lehnerreisenconsole.model.Reiseveranstaltung;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Simon
 */
@Named("eventcon")
@SessionScoped
public class EventController implements Serializable {

    @Inject
    private DAO dao;
    
    @Inject 
    private UserController usercon;

    private Reiseveranstaltung show;
    
    @PostConstruct
    public void init() {
        show = null;
    }
    
    public DAO getDao() {
        return dao;
    }
    
    
    public Set<Reiseveranstaltung> getVeranstaltungen(Reisetyp typ) {
        return dao.getVeranstaltungen(typ);
    }
    
    public void setShow(Reiseveranstaltung show) {
        System.out.println(show);
        this.show = show;
    }
    
    public Reiseveranstaltung getShow() {
        return show;
    }
    
}
