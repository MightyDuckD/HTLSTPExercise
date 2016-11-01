/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 *
 * @author Simon
 */
public class Prize implements  Serializable {
    private Timestamp time;
    private int cent;
    private double discount;

    public Prize(int cent, double discount) {
        this.time = new Timestamp(System.currentTimeMillis());
        this.cent = cent;
        this.discount = discount;
    }

    public int getCent() {
        return cent;
    }

    public double getDiscount() {
        return discount;
    }
    
    
    @Override
    public String toString() {
        return String.format("%.2f", cent/100.0);
    }
    
}
