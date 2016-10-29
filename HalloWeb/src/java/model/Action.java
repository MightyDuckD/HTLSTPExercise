/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

/**
 *
 * @author 20120093
 */
@Entity
@SequenceGenerator(name = "actionseq", initialValue = 10000, allocationSize = 1)
public class Action implements Serializable {

    public static Comparator<? super Action> COMPARE_BY_TIMESTAMP = (a, b) -> {
        return a.getCreateDate().compareTo(b.getCreateDate());
    };

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actionseq")
    private int id;

    @Column
    String text;

    @Column
    @Type(type = "timestamp")
    private Timestamp createDate;

    public Action() {
    }

    public Action(Timestamp createDate, String text) {
        this.createDate = createDate;
        this.text = text;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getDecoration() {
        if("contextInitialized".equals(text) || "contextDestroyed".equals(text))
            return "danger";
        if(text != null && text.endsWith(".init() called"))
            return "success";
        return "";
    }

}
