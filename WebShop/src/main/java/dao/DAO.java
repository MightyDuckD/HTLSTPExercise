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
import model.Kategorie;
import model.User;

/**
 * Public
 * @author Simon
 */
public interface DAO {
    
    //USER
    User getUserByUsername(String name);
    
    
    //ARTIKEL
    List<Artikel> getAllArtikel();
    List<Artikel> getAllArtikelByKategorie(Kategorie k);
    Artikel getArtikelById(int id);
    
    //KAT
    List<Kategorie> getAllKategorien();
    Kategorie getKategorieById(int id);
    List<Kategorie> getChildrenById(int id);
    
    void close();
    void open(ServletContext servletContext);

    public Kategorie getKategorieByPath(String path);
    
    void dumpToJson(OutputStream output);
    void readFromJson(InputStream input);

    
}
