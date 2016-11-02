/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Simon
 */
@Entity
@Table(name = "warenkorb")
//ignore netbeans error here - if the Users object has an id hibernate throws a nullpointer exception
public class Warenkorb implements Serializable {

    @Id
    private String username;

    @MapsId
    @OneToOne(mappedBy = "warenkorb", cascade = CascadeType.ALL)
    @JoinColumn(name = "username") 
    private Users users;

    @Column
    private Timestamp created;

    @OneToMany()
    private List<WarenkorbItem> items;

    public Warenkorb() {
        created = new Timestamp(System.currentTimeMillis());
        items = new ArrayList<>();
    }

    public void addItem(WarenkorbItem item) {
        items.add(item);

    }

    public WarenkorbItem getItemById(int id) {
        for (WarenkorbItem item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void removeItemById(int id) {
        items.remove(getItemById(id));
    }

    public Timestamp getCreated() {
        return created;
    }

    public List<WarenkorbItem> getItems() {
        return new ArrayList<>(items);
    }

}
