/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import model.ToDo;

/**
 *
 * @author 20120093
 */
@Named(value = "toDoCon")
@SessionScoped
public class ToDoController implements Serializable {
    
    private List<ToDo> todos;
    private ToDo newToDo;
    
    private final static String[] COLORS = {"lightgray","lightgreen","yellow","red"};
    
    @PostConstruct
    public void init() {
        this.todos = new ArrayList<>();
        this.todos.add(new ToDo("POS lernen",new GregorianCalendar(2016,10,2).getTime(),3));
        this.todos.add(new ToDo("Gitarre lernen", new GregorianCalendar(2016,10,1).getTime(),2));
        this.newToDo = new ToDo();
    }

    public List<ToDo> getTodos() {
        return todos;
    }

    public void setTodos(List<ToDo> todos) {
        this.todos = todos;
    }

    public ToDo getNewToDo() {
        return newToDo;
    }

    public void setNewToDo(ToDo newToDo) {
        this.newToDo = newToDo;
    }
    
    public String getPriortiyColor(ToDo akt) {
        return akt.isDone() ? COLORS[0] : COLORS[akt.getPriority()];
    }
    
    public void saveNewTodo() {
        todos.add(newToDo);
        newToDo = new ToDo();
    }
    
}
