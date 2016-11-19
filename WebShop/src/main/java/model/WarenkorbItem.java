/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Simon
 */
public class WarenkorbItem implements Serializable {
    
    private Integer id;
    
    private Integer amount;
    
    private Artikel artikel;

    public WarenkorbItem() {
        this(null,0,null);
    }

    public WarenkorbItem(Integer id, Integer amount, Artikel artikel) {
        this.id = id;
        this.amount = amount;
        this.artikel = artikel;
    }

    public int getAmount() {
        return amount;
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public int getId() {
        return id;
    }
    
}
