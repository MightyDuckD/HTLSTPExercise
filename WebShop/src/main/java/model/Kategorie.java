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
public class Kategorie implements  Serializable{

    private Kategorie parent;
    private int id;
    private String name;
    private String path;


    public Kategorie(int id, String name, String path,Kategorie parent) {
        this.parent = parent;
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public Kategorie getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
    

    public int getId() {
        return id;
    }

}
