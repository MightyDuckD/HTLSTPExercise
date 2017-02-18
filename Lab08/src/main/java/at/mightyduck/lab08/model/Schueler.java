package at.mightyduck.lab08.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author reio
 */
public class Schueler implements Serializable {

    private int nr;
    private String nachname;
    private String vorname;
    private char geschlecht;

    private static final List<Schueler> klasse;

    static {
        klasse = new ArrayList<>();

        klasse.add(new Schueler(1, "Ehn", "Wilhelm", 'M'));
        klasse.add(new Schueler(2, "Gruber", "Sarah", 'W'));
        klasse.add(new Schueler(3, "Guthan", "Raphael", 'M'));
        klasse.add(new Schueler(4, "Hamberger", "Sebastian", 'M'));
        klasse.add(new Schueler(5, "Harold", "Sascha", 'M'));
        klasse.add(new Schueler(6, "Kornberger", "Jürgen", 'M'));
        klasse.add(new Schueler(7, "Navratil", "Philipp", 'M'));
        klasse.add(new Schueler(8, "Pfeiffer", "Michael", 'M'));
        klasse.add(new Schueler(9, "Purker", "Angela", 'W'));
        klasse.add(new Schueler(10, "Rasch", "Patrick", 'M'));
        klasse.add(new Schueler(11, "Ringelhahn", "Carina", 'W'));
        klasse.add(new Schueler(12, "Sattler", "Benedikt", 'M'));
        klasse.add(new Schueler(13, "Schirmer", "Kurt", 'M'));
        klasse.add(new Schueler(14, "Schneider", "Florens", 'M'));
        klasse.add(new Schueler(15, "Simmer", "Patrick", 'M'));
        klasse.add(new Schueler(16, "Staudinger", "Patrik", 'M'));
        klasse.add(new Schueler(17, "Tatzreiter", "Oliver", 'M'));
        klasse.add(new Schueler(18, "Tröscher", "Dominik", 'M'));
    }

    public Schueler(int nr) {
        this(nr, "", "", 'M');
    }

    public Schueler(int nr, String nachname, String vorname, char geschlecht) {
        this.nr = nr;
        this.nachname = nachname;
        this.vorname = vorname;
        this.geschlecht = geschlecht;
    }

    public static List<Schueler> getKlasse() {
        return klasse;
    }

    public String getNachname() {
        return nachname;
    }

    public int getNr() {
        return nr;
    }

    public String getVorname() {
        return vorname;
    }

    public char getGeschlecht() {
        return geschlecht;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public void setGeschlecht(char geschlecht) {
        this.geschlecht = geschlecht;
    }

    @Override
    public String toString() {
        return String.format("%02d %s %s", nr, vorname, nachname);
    }

}
