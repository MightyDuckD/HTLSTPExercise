/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author Simon
 */
public class Person {

    private String zuname;
    private String vorname;
    private Date geburtstag;
    private Hobby hobby;
    private Boolean weiblich;

    public Person() {
//        this("", "", new Date(), Hobby.getHobbies().get(0), false);
    }

    public Person(String zuname, String vorname, Date geburtstag, Hobby hobby, boolean weiblich) {
        this.zuname = zuname;
        this.vorname = vorname;
        this.geburtstag = geburtstag;
        this.hobby = hobby;
        this.weiblich = weiblich;
    }

    public Date getGeburtstag() {
        return geburtstag;
    }

    public Hobby getHobby() {
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

    public void setHobby(Hobby hobby) {
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

}
