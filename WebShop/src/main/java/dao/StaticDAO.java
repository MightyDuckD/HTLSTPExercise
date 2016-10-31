/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import model.Adresse;
import model.User;
import util.UserUtil;

/**
 * @author Simon
 */
public class StaticDAO implements DAO {

    private ServletContext context;
    private List<User> user = new ArrayList<>();

    @Override
    public User getUserByUsername(String username) {
        for (User u : user) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void open() {
        user.add(UserUtil.create("user01", "pwd01"));
        user.add(UserUtil.create("user02", "pwd02"));
        user.add(UserUtil.create("user03", "pwd03"));
        user.add(UserUtil.create("user04", "pwd04"));
        user.add(UserUtil.create("user05", "pwd05"));
    }

}
