/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Simon
 */
public class Category implements Serializable {

    private int id;
    private String bezeichnung;
    private String path;

    private Category parent;
    
    private Set<Category> childs;

    private Set<Artikel> artikel = new HashSet<>();

    public Category() {
        this(0, "", "", null);
    }

    public Category(int id, String name, String path, Category parent) {
        this.parent = parent;
        this.id = id;
        this.bezeichnung = name;
        this.path = path;
    }

    public Category getParent() {
        return parent;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public String getPath() {
        return path;
    }

    public int getId() {
        return id;
    }

    public Set<Artikel> getArtikel() {
        return artikel;
    }

}
