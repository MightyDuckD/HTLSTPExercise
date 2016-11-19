/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import model.Artikel;
import model.Category;
import model.Users;
import model.Warenkorb;

/**
 * @author Simon
 */
public class StaticDAO implements DAO {

    private transient ServletContext context;

    private List<Users> user = new ArrayList<>();
    private List<Artikel> artikel = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    @Override
    public Users getUserByUsername(String username) {
        System.out.println("username");
        for (Users u : user) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<Artikel> getAllArtikel() {
        return new ArrayList<>(artikel);
    }

    @Override
    public Artikel getArtikelById(int id) {
        for (Artikel a : artikel) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    @Override
    public void open(ServletContext servletContext) {
    }
    @Override
    public void close() {

    }


    @Override
    public List<Category> getAllCategorien() {
        return new ArrayList<>(categories);
    }

    @Override
    public List<Artikel> getAllArtikelByKategorie(Category k) {
        System.out.println("searching for " + k);
        List<Artikel> artikel = new ArrayList<>();
        for (Artikel a : getAllArtikel()) {
            for (Category ak : a.getCategories()) {
                System.out.println(a + " " + ak);
                while (ak != null) {
                    System.out.println(ak.getId() + " " + k.getId());
                    if (ak.getId() == k.getId()) {
                        artikel.add(a);
                        break;
                    }
                    ak = ak.getParent();
                }
            }
        }
        return artikel;
    }

    @Override
    public List<Category> getChildrenById(int id) {
        List<Category> result = new ArrayList<>();
        for (Category k : categories) {
            if (k.getParent() != null && k.getParent().getId() == id) {
                result.add(k);
            }
        }
        return result;
    }

    @Override
    public Category getCategoryById(int i) {
        for (Category k : categories) {
            if (k.getId() == i) {
                return k;
            }
        }
        return null;
    }

    @Override
    public Category getKategorieByPath(String path) {
        System.out.println("get by path " + path);
        for (Category k : categories) {
            if (k.getPath().equals(path)) {
                return k;
            }
        }
        return null;
    }

    @Override
    public void addArtikel(Artikel artikel) {
        this.artikel.add(artikel);
    }

    @Override
    public void addCategory(Category category) {
        this.categories.add(category);
    }

    @Override
    public void addUser(Users user) {
        this.user.add(user);
    }

    
}
