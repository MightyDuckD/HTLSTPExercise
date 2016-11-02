/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.ServletContext;
import model.Artikel;
import model.Category;
import model.Users;
import model.Warenkorb;

/**
 * Public
 *
 * @author Simon
 */
public interface DAO {

    //USER
    void addUser(Users user);

    Users getUserByUsername(String name);

    //ARTIKEL
    void addArtikel(Artikel artikel);

    List<Artikel> getAllArtikel();

    List<Artikel> getAllArtikelByKategorie(Category k);

    Artikel getArtikelById(int id);

    //KAT
    void addCategory(Category category);

    List<Category> getAllCategorien();

    Category getCategoryById(int id);

    List<Category> getChildrenById(int id);

    void close();

    void open(ServletContext servletContext);

    public Category getKategorieByPath(String path);

    void dumpToJson(OutputStream output);

    void readFromJson(InputStream input);

    void persist(Object o);

}
