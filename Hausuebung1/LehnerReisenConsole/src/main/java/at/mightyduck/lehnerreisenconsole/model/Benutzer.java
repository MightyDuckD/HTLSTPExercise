/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lehnerreisenconsole.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
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
    
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "benutzer_reisetyp", joinColumns = @JoinColumn(name = "emailid"), inverseJoinColumns = @JoinColumn(name = "reiseid"))
    private Set<Reisetyp> interessen;

    public Benutzer() {
        this(null, null, null);
    }

    public Benutzer(String email, String passwort, String salt) {
        this(email, passwort, salt, false);
    }

    public Benutzer(String email, String passwort, String salt, boolean newsletter) {
        this.email = email;
        this.passwort = passwort;
        this.salt = salt;
        this.newsletter = newsletter;
        this.interessen = new HashSet<>();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public void setInteressen(Set<Reisetyp> interessen) {
        this.interessen = interessen;
    }

    public void setNewsletter(boolean newsletter) {
        System.out.println(email + " " + newsletter);
        this.newsletter = newsletter;
    }

    @Override
    public String toString() {
        return "Benutzer{" + "email=" + email + ", passwort=" + passwort + ", salt=" + salt + ", newsletter=" + newsletter + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.email);
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
        final Benutzer other = (Benutzer) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    
    
}
