/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lehnerreisenconsole.model;

import java.io.Serializable;
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
    private Integer id;

    @Column
    private String bezeichnung;
    
    @ManyToMany(mappedBy = "interessen")
    private Set<Benutzer> interessenten;

    public Reisetyp() {
        this(null,null);
    }

    public Reisetyp(Integer id, String bezeichnung) {
        this.id = id;
        this.bezeichnung = bezeichnung;
    }

    public Integer getId() {
        return id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public Set<Benutzer> getInteressenten() {
        return interessenten;
    }

    @Override
    public String toString() {
        return "Reisetyp{" + "id=" + id + ", bezeichnung=" + bezeichnung + '}';
    }
}
