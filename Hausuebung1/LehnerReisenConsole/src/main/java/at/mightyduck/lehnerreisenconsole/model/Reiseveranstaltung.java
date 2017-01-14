/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lehnerreisenconsole.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Simon
 */
@Entity
@Table
public class Reiseveranstaltung implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String zielort, beschreibung, hyperlink;

    @Column
    @Temporal(TemporalType.DATE)
    private Date beginn, ende;

    @Column
    private Double preis;
    
    @ManyToOne
    private Reisetyp typ;

    public Reiseveranstaltung() {
        this(null, null, null, null, null, null, null);
    }

    public Reiseveranstaltung(Integer id, String zielort, String beschreibung, String hyperlink, Date beginn, Date ende, Double preis) {
        this.id = id;
        this.zielort = zielort;
        this.beschreibung = beschreibung;
        this.hyperlink = hyperlink;
        this.beginn = beginn;
        this.ende = ende;
        this.preis = preis;
    }

    public Integer getId() {
        return id;
    }

    public String getZielort() {
        return zielort;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public Date getBeginn() {
        return beginn;
    }

    public Date getEnde() {
        return ende;
    }

    public Double getPreis() {
        return preis;
    }

    public Reisetyp getTyp() {
        return typ;
    }

    @Override
    public String toString() {
        return "Reiseveranstaltung{" + "id=" + id + ", zielort=" + zielort + ", beschreibung=" + beschreibung.replace("\n", "\\n") + ", hyperlink=" + hyperlink + ", beginn=" + beginn + ", ende=" + ende + ", preis=" + preis + '}';
    }

    
}
