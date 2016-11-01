/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Simon
 */
public class Artikel  implements  Serializable{

    private int id;
    private String name;
    private String htmlTextShort;
    private String htmlTextLong;
    private String urlImageIcon;
    private String urlImageLarge;

    private List<Kategorie> kategorie;
    private List<Prize> prize;

    public Artikel() {
    }

    public Artikel(int id, String name, Prize start, Kategorie... k) {
        this(id, name, "", "", "no_img_avail.svg", "no_img_avail.svg", start, k);
    }

    public Artikel(int id, String name, String htmlTextShort, String htmlTextLong, String urlImageIcon, String urlImageLarge, Prize start, Kategorie... kategories) {
        this.id = id;
        this.name = name;
        this.htmlTextShort = htmlTextShort;
        this.htmlTextLong = htmlTextLong;
        this.urlImageIcon = urlImageIcon;
        this.urlImageLarge = urlImageLarge;
        this.prize = new ArrayList<>();
        this.kategorie = new ArrayList<>();
        for (Kategorie k : kategories) {
            kategorie.add(k);
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

    public String getName() {
        return name;
    }

    public Prize getCurrentPrize() {
        return prize.get(0);
    }

    public List<Prize> getAllPrize() {
        return prize;
    }

    public List<Kategorie> getAllKategorie() {
        return kategorie;
    }

    public Prize getPrize() {
        return getAllPrize().get(0);
    }

}
