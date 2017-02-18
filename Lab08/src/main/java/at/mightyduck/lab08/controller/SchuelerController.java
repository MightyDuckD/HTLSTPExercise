/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lab08.controller;

import at.mightyduck.lab08.model.Schueler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Simon
 */
@Named("schuelercon")
@SessionScoped
public class SchuelerController implements Serializable {

    private List<Schueler> schueler, filtered, selected;
    private Schueler newSchueler;

    public List<Schueler> getSchueler() {
        return schueler;
    }

    public List<Schueler> getFilteredSchueler() {
        return filtered;
    }

    public List<Schueler> getSelectedSchueler() {
        return selected;
    }

    public void setSelectedSchueler(List<Schueler> selected) {
        this.selected = selected;
    }

    public void setFilteredSchueler(List<Schueler> filtered) {
        this.filtered = filtered;
    }

    public void setSchueler(List<Schueler> schueler) {
        this.schueler = schueler;
    }

    public Schueler getNewSchueler() {
        return newSchueler;
    }

    public void setNewSchueler(Schueler newSchueler) {
        this.newSchueler = newSchueler;
    }
    
    private void initNewSchueler() {
        Set<Integer> ids = new TreeSet<>();
        for (Schueler s : schueler) {
            ids.add(s.getNr());
        }
        int lower = 1;
        for (int upper : ids) {
            if (upper - lower > 1) {
                break;
            }
            lower = upper;
        }
        this.newSchueler = new Schueler(lower + 1);
    }

    public void addNewSchueler() {
        this.schueler.add(newSchueler);
        this.initNewSchueler();
    }

    public int sortByVorname(Object a, Object b) {
        return ((Schueler) a).getVorname().compareTo(((Schueler) b).getVorname());
    }

    @PostConstruct
    public void init() {
        this.schueler = Schueler.getKlasse();
        this.filtered = new ArrayList<>();
        this.selected = new ArrayList<>();
         initNewSchueler();
    }

}
