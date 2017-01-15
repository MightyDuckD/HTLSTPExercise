/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lehnerreisenconsole.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
    private Integer verid;

    @Column(nullable = false,length = 30)
    private String zielort;

    @Column(length = 300)
    private String beschreibung;

    @Column
    private String hyperlink;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date beginn;

    @Column
    @Temporal(TemporalType.DATE)
    private Date ende;

    @Column(nullable = false)
    private Double preis;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Reisetyp typ;

    @Column
    private String image;

    public Reiseveranstaltung() {
        this(null, null, null, null, null, null, null, null, null);
    }

    public Reiseveranstaltung(Integer id, String zielort, String beschreibung, String hyperlink, Date beginn, Date ende, Double preis, Reisetyp typ, String image) {
        this.verid = id;
        this.zielort = zielort;
        this.beschreibung = beschreibung;
        this.hyperlink = hyperlink;
        this.beginn = beginn;
        this.ende = ende;
        this.preis = preis;
        this.typ = typ;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setVerid(Integer verid) {
        this.verid = verid;
    }

    public void setZielort(String zielort) {
        this.zielort = zielort;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

    public void setBeginn(Date beginn) {
        this.beginn = beginn;
    }

    public void setEnde(Date ende) {
        this.ende = ende;
    }

    public void setPreis(Double preis) {
        this.preis = preis;
    }

    public void setTyp(Reisetyp typ) {
        this.typ = typ;
    }

    public Integer getVerid() {
        return verid;
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
        return "Reiseveranstaltung{" + "id=" + verid + ", zielort=" + zielort + ", beschreibung=" + beschreibung.replace("\n", "\\n") + ", hyperlink=" + hyperlink + ", beginn=" + beginn + ", ende=" + ende + ", preis=" + preis + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.verid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        final Reiseveranstaltung other = (Reiseveranstaltung) obj;
        if (!Objects.equals(this.verid, other.verid)) {
            return false;
        }
        return true;
    }

}
