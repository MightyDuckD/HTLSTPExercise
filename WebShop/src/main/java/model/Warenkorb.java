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

/**
 *
 * @author Simon
 */
public class Warenkorb  implements  Serializable{
    
    private Timestamp created;
    private List<WarenkorbItem> items;

    public Warenkorb() {
        created = new Timestamp(System.currentTimeMillis());
        items = new ArrayList<>();
    }

    public void addItem(WarenkorbItem item) {
        items.add(item);
    }
    
    public WarenkorbItem getItemById(int id) {
        for(WarenkorbItem item : items) {
            if(item.getId() == id)
                return item;
        }
        return null;
    }

    public void removeItemById(int id) {
        items.remove(getItemById(id));
    }
    
    public static class WarenkorbItem {
        private int id;
        private int amount;
        private Artikel artikel;

        public WarenkorbItem(int id,int amount, Artikel artikel) {
            this.id = id;
            this.amount = amount;
            this.artikel = artikel;
        }

        public int getAmount() {
            return amount;
        }

        public Artikel getArtikel() {
            return artikel;
        }

        public int getId() {
            return id;
        }
        
    }

    public Timestamp getCreated() {
        return created;
    }

    public List<WarenkorbItem> getItems() {
        return new ArrayList<>(items);
    }
    
    
}
