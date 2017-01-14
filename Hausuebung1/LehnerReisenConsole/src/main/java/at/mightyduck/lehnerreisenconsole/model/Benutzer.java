/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lehnerreisenconsole.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author Simon
 */
@Entity
@Table
public class Benutzer implements Serializable {

    @Id
    @Column
    private String email;

    @Column
    private String passwort, salt;

    @Column
    private boolean newsletter;
    
    @ManyToMany
    private Set<Reisetyp> interessen;

    public Benutzer() {
        this(null, null, null, false);
    }

    public Benutzer(String email, String passwort, String salt, boolean newsletter) {
        this.email = email;
        this.passwort = passwort;
        this.salt = salt;
        this.newsletter = newsletter;
        this.interessen = new HashSet<>();
    }

    public String getEmail() {
        return email;
    }

    public String getPasswort() {
        return passwort;
    }

    public String getSalt() {
        return salt;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public Set<Reisetyp> getInteressen() {
        return interessen;
    }
    
    @Override
    public String toString() {
        return "Benutzer{" + "email=" + email + ", passwort=" + passwort + ", salt=" + salt + ", newsletter=" + newsletter + '}';
    }

}
