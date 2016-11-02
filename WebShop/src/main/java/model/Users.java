/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.ejb.TransactionAttribute;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Simon
 */
@Entity
@Table(name = "users")
public class Users implements Serializable {

    @Id
    @Column(name = "username")
    private String username;
    @Column
    private String password;
    @Column
    private String salt;

    @Column
    private String vorname;
    @Column
    private String nachname;

    @Embedded
    private Adresse home;

    @OneToOne(optional = true)
    @PrimaryKeyJoinColumn
    private Warenkorb warenkorb;

    public Users() {
        this("", "", "", new Adresse());
    }

    public Users(String name, String salt, String password, Adresse home) {
        this.username = name;
        this.salt = salt;
        this.password = password;
        this.home = home;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Adresse getHome() {
        return home;
    }

    public String getSalt() {
        return salt;
    }

    public String getNachname() {
        return nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public Warenkorb getWarenkorb() {
        return warenkorb;
    }

    public void setWarenkorb(Warenkorb warenkorb) {
        this.warenkorb = warenkorb;
    }

}
