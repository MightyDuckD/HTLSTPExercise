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
@Entity
@Table(name = "artikel")
public class Artikel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "artikelId")
    private Integer id;

    @Column(name = "bezeichnung")
    private String bezeichnung;
    @Column(name = "htmlTextShort")
    private String htmlTextShort;
    @Column(name = "htmlTextLong")
    private String htmlTextLong;
    @Column(name = "urlImageIcon")
    private String urlImageIcon;
    @Column(name = "urlImageLarge")
    private String urlImageLarge;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "artikel_category", joinColumns = {
        @JoinColumn(name = "artikelId", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "categoryId",
                        nullable = false, updatable = false)})
    private Set<Category> categories;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "prize")
    @JoinColumn(name = "artikelId")
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
