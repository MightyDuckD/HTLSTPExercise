/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Simon
 */
public class Artikel implements Serializable {

    
    private Integer id;

    private String bezeichnung;
    private String htmlTextShort;
    private String htmlTextLong;
    private String urlImageIcon;
    private String urlImageLarge;
    
    private Set<Category> categories;
    
    private List<Prize> prize;

    public Artikel() {
        this(null, "", new Prize(0, 0));
    }

    public Artikel(Integer id, String name, Prize start, Category... k) {
        this(id, name, "", "", "no_img_avail.svg", "no_img_avail.svg", start, k);
    }

    public Artikel(Integer id, String name, String htmlTextShort, String htmlTextLong, String urlImageIcon, String urlImageLarge, Prize start, Category... categories) {
        this.id = id;
        this.bezeichnung = name;
        this.htmlTextShort = htmlTextShort;
        this.htmlTextLong = htmlTextLong;
        this.urlImageIcon = urlImageIcon;
        this.urlImageLarge = urlImageLarge;
        this.prize = new ArrayList<>();
        this.categories = new HashSet<>();
        for (Category k : categories) {
            this.categories.add(k);
        }
        this.prize.add(start);
    }

    public String getHtmlTextLong() {
        return htmlTextLong;
    }

    public String getHtmlTextShort() {
        return htmlTextShort;
    }

    public int getId() {
        return id;
    }

    public String getImageIcon() {
        return urlImageIcon;
    }

    public String getImageLarge() {
        return urlImageLarge;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public Prize getCurrentPrize() {
        return prize.get(0);
    }

    public List<Prize> getAllPrize() {
        return prize;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Prize getPrize() {
        return getAllPrize().get(0);
    }

}
