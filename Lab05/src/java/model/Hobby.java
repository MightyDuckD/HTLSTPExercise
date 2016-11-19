/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Simon
 */
public class Hobby {

    private String bezeichnung;

    public Hobby() {
        this.bezeichnung = "";
    }

    public Hobby(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public static List<Hobby> getHobbies() {
        return Arrays.asList(
                new Hobby("Zeichnen"),
                new Hobby("Lesen"),
                new Hobby("Schwimmen"),
                new Hobby("Fernsehen"),
                new Hobby("Radfahren")
        );
    }

}
