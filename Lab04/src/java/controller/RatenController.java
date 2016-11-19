/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 *
 * @author Simon
 */
@Named(value = "raten")
@SessionScoped
public class RatenController implements Serializable {

    private static Random rnd = new Random();
    private Integer n; // Anzahl der Versuche im Spiel
    private Integer aktVersuch; // aktueller Versuch
    private Integer secretnr; // Geheimzahl
    private List<String> msg; // Liste der Meldungen
    private Boolean ende; // Flag fuer Spielende

    @PostConstruct
    public void init() {
        neuesSpiel(null);
    }

    public void neuesSpiel(ActionEvent e) {
        n = 0;
        aktVersuch = 0;
        secretnr = rnd.nextInt(1000);
        ende = false;
        msg = new ArrayList<>();
    }

    public void neuerVersuch(ActionEvent e) {
        // erhoeht n
        // anaysieren von aktnr (Benutzereingabe)
        // und erzeugen einer entsprechenden Meldung
        // Gegebnfalls Endeflag setzen
        n++;
        if (aktVersuch < secretnr) {
            msg.add("Die gesuchte Zahl ist größer als " + aktVersuch);
        } else if (aktVersuch > secretnr) {
            msg.add("Die gesuchte Zahl ist kleiner als " + aktVersuch);
        } else {
            msg.add("Die gesuchte Zahl wurde im " + n + ". Versuch erraten");
            ende = true;
        }
    }

    public Boolean getEnde() {
        return ende;
    }

    public List<String> getMsg() {
        return msg;
    }

    public Integer getAktVersuch() {
        return aktVersuch;
    }

    public void setAktVersuch(Integer aktVersuch) {
        this.aktVersuch = aktVersuch;
    }
    

}
