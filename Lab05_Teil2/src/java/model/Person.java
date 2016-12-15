/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.Dependent;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Simon
 */
@Dependent
@Entity
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String zuname;
    @Column
    private String vorname;
    
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date geburtstag;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Hobby> hobby;
    @Column
    private Boolean weiblich;

    public Person() {
        this(null,null,null, new ArrayList<>(), false);
    }

    public Person(String zuname, String vorname, Date geburtstag, List<Hobby> hobby, boolean weiblich) {
        this.zuname = zuname;
        this.vorname = vorname;
        this.geburtstag = geburtstag;
        this.hobby = new ArrayList<>(hobby);
        this.weiblich = weiblich;
    }

    public Date getGeburtstag() {
        return geburtstag;
    }

    public List<Hobby> getHobby() {
        return hobby;
    }

    public String getVorname() {
        return vorname;
    }

    public String getZuname() {
        return zuname;
    }

    public Boolean isWeiblich() {
        return weiblich;
    }

    public Boolean getWeiblich() {
        return weiblich;
    }

    public void setGeburtstag(Date geburtstag) {
        this.geburtstag = geburtstag;
    }

    public void setHobby(List<Hobby> hobby) {
        this.hobby = hobby;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setWeiblich(Boolean weiblich) {
        this.weiblich = weiblich;
    }

    public void setZuname(String zuname) {
        this.zuname = zuname;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.id, ((Person) obj).id);
    }
}
