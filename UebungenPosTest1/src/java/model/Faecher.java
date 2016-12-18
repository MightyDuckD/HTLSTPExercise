/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Simon
 */
public class Faecher {

    public static enum Fach {

        AM("Mathematik"),
        PR("Programmieren"),
        D("Deutsch"),
        E("Englisch");
        public final String name;

        private Fach(String name) {
            this.name = name;
        }
    }
    private int counts[] = new int[Fach.values().length];

    public int getCount(Fach fach) {
        return counts[fach.ordinal()];
    }
    
    public void increase(Fach fach){
        counts[fach.ordinal()] ++;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Statistik über die gewählten Fächer:\n");
        for (Fach f : Fach.values()) {
            builder.append("   ").append(f.name).append(" wurde ").append(getCount(f)).append(" mal gewählt.\n");
        }
        return builder.toString();
    }

}
 