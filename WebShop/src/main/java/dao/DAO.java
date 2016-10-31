/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.User;

/**
 *
 * @author Simon
 */
public interface DAO {
    
    
    User getUserByUsername(String name);

    public void close();

    public void open();
    
}
