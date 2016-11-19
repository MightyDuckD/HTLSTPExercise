/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import model.ToDo;

/**
 *
 * @author Simon
 */
@Named(value = "flowCon")
@SessionScoped
public class WebsiteFlowController implements Serializable {
    
    private String previous,current;

    @PostConstruct
    public void init() {
        previous = "";
        current = "start";
    }
    
    public String next() {
        switch(current) {
            case "default": 
            case "victory": return "Go to start";
            case "start": return "Go to first or default";
            case "first": return "Go to second or default";
            case "second": return "Go to victory or default";
            default: return "unkown state";
        }
    }
    
    public String doit() {
        previous = current;
        current = getNextPage(current);
        return current + "?faces-redirect=true";
    }
    
    public String getNextPage(String from) {
        switch(from) {
            case "default":
            case "victory": return "start";
            case "start": return (Math.random() < 0.5)?"default":"first";
            case "first": return (Math.random() < 0.5)?"default":"second";
            case "second": return (Math.random() < 0.5)?"default":"victory";
            default: return "default";
        }
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getPrevious() {
        return previous;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }
    
}
