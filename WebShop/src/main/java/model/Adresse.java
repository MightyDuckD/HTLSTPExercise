/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Simon
 */
public class Adresse implements  Serializable {
    
    private String street,town;
    private int postalcode;

    public Adresse() {
        this("","",0);
    }

    public Adresse(String street, String town, int postalcode) {
        this.street = street;
        this.town = town;
        this.postalcode = postalcode;
    }

    public int getPostalcode() {
        return postalcode;
    }

    public String getStreet() {
        return street;
    }

    public String getTown() {
        return town;
    }

    @Override
    public String toString() {
        return street + "\n" + postalcode + " " + town;
    }
    
}
