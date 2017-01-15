/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lehnerreisenconsole.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author Simon
 */
@Entity
@Table
public class Reisetyp implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reiseid;

    @Column
    private String bezeichnung;
    

    @OneToMany(cascade=CascadeType.ALL, mappedBy="typ")  
    private Set<Reiseveranstaltung> veranstaltungen;
    
    public Reisetyp() {
        this(null,null);
    }

    public Reisetyp(Integer reiseid, String bezeichnung) {
        this.reiseid = reiseid;
        this.bezeichnung = bezeichnung;
    }

    public void setReiseid(Integer reiseid) {
        this.reiseid = reiseid;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    
    public Integer getReiseid() {
        return reiseid;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setVeranstaltungen(Set<Reiseveranstaltung> veranstaltungen) {
        this.veranstaltungen = veranstaltungen;
    }

    public Set<Reiseveranstaltung> getVeranstaltungen() {
        return veranstaltungen;
    }

    @Override
    public String toString() {
        return "Reisetyp{" + "id=" + reiseid + ", bezeichnung=" + bezeichnung + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.reiseid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Objects.equals(getClass(),obj.getClass())) {
            return false;
        }
        final Reisetyp other = (Reisetyp) obj;
        if (!Objects.equals(this.reiseid, other.reiseid)) {
            return false;
        }
        return true;
    }
    
    
}
