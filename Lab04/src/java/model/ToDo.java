/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author 20120093
 */
public class ToDo {
    
    private String description;
    private Date date;
    private int priority;
    private boolean done;

    public ToDo() {
        this("description",new Date(),1);
    }

    public ToDo(String description, Date date, int priority) {
        this.description = description;
        this.date = date;
        this.priority = priority;
        this.done = false;
    }

    @Override
    public String toString() {
        return "ToDo{" + "description=" + description + ", date=" + date + ", priority=" + priority + ", done=" + done + '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
    
    
    
}
