/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import model.Action;

/**
 *
 * @author 20120093
 */
public interface DAO extends AutoCloseable {
    boolean saveAction(Action a);
    List<Action> getAllActions();
    List<Action> getAllActionsSorted(int limit);
    boolean dropAll();
    void open();
}
