/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import model.Artikel;
import model.Kategorie;
import model.Prize;
import model.User;
import util.UserUtil;

/**
 * @author Simon
 */
public class StaticDAO implements DAO {

    
    private transient ServletContext context;
    
    private List<User> user = new ArrayList<>();
    private List<Artikel> artikel = new ArrayList<>();
    private List<Kategorie> kategorien = new ArrayList<>();

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
    public void close() {

    }

    @Override
    public void open(ServletContext servletContext) {
        user.add(UserUtil.create("user01", "pwd01"));
        user.add(UserUtil.create("user02", "pwd02"));
        user.add(UserUtil.create("user03", "pwd03"));
        user.add(UserUtil.create("user04", "pwd04"));
        user.add(UserUtil.create("user05", "pwd05"));

        kategorien.add(new Kategorie(0, "New", "", null));

        kategorien.add(new Kategorie(1, "Clothing", "Clothing", null));
        kategorien.add(new Kategorie(11, "T-Shirts", "Clothing/TShirt", getKategorieById(1)));
        kategorien.add(new Kategorie(12, "Hoodies", "Clothing/Hoodies", getKategorieById(1)));
        kategorien.add(new Kategorie(13, "Jackets &amp Outerwear", "Clothing/JacketsOuterwear", getKategorieById(1)));

        kategorien.add(new Kategorie(2, "Toys &amp; Games", "ToysGames", null));
        kategorien.add(new Kategorie(21, "Puzzles", "ToysGames/Puzzles", getKategorieById(2)));
        kategorien.add(new Kategorie(22, "DIY &amp; Sciencce Toys", "ToysGames/DIYScienceToys", getKategorieById(2)));

        kategorien.add(new Kategorie(3, "Home &amp; Office", "HomeOffice", null));
        kategorien.add(new Kategorie(31, "Blankets, Rugs &amp; Towels", "HomeOffice/BlanketsRugsTowels", getKategorieById(3)));
        kategorien.add(new Kategorie(32, "Coffee Mugs &amp; Travel Mugs", "HomeOffice/CoffeeMugsTravelMugs", getKategorieById(3)));
        kategorien.add(new Kategorie(33, "Glassware &amp; Drinkware", "HomeOffice/GlasswareDrinkware", getKategorieById(3)));

        kategorien.add(new Kategorie(4, "On Sale", "Sale", null));

        artikel.add(new Artikel(1, "Caffeine Mug", new Prize(799, 0.5), getKategorieById(32)));
        artikel.add(new Artikel(2, "Caffeine Mug", new Prize(799, 0.5), getKategorieById(32)));
        artikel.add(new Artikel(3, "Caffeine Mug", new Prize(799, 0.5), getKategorieById(32)));
        artikel.add(new Artikel(4, "Caffeine Mug", "short", "long", "icon", "image", new Prize(799, 0.5), getKategorieById(32)));

        dumpToJson(System.out);
        readFromJson(getClass().getClassLoader().getResourceAsStream("/data.json"));
    }
    
    @Override
    public void dumpToJson(OutputStream output) {
        try {
            Writer writer = new OutputStreamWriter(output);
            Gson gson = new Gson();
            String out = gson.toJson(this);
            writer.append(out);
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(StaticDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void readFromJson(InputStream input) {
        kategorien.clear();
        artikel.clear();
        user.clear();
        System.out.println("cleared all things");
        if(input == null)
            return;
        System.out.println("loading json file");
        Gson gson = new Gson();
        
        StaticDAO oo = gson.fromJson(new InputStreamReader(input), StaticDAO.class);
        user = (List<User>) oo.user;
        artikel = (List<Artikel>) oo.artikel;
        kategorien = (List<Kategorie>) oo.kategorien;
    }

    @Override
    public List<Kategorie> getAllKategorien() {
        return new ArrayList<>(kategorien);
    }

    @Override
    public List<Artikel> getAllArtikelByKategorie(Kategorie k) {
        System.out.println("searching for " + k);
        List<Artikel> artikel = new ArrayList<>();
        for (Artikel a : getAllArtikel()) {
            for (Kategorie ak : a.getAllKategorie()) {
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
    public List<Kategorie> getChildrenById(int id) {
        List<Kategorie> result = new ArrayList<>();
        for (Kategorie k : kategorien) {
            if (k.getParent() != null && k.getParent().getId() == id) {
                result.add(k);
            }
        }
        return result;
    }

    @Override
    public Kategorie getKategorieById(int i) {
        for (Kategorie k : kategorien) {
            if (k.getId() == i) {
                return k;
            }
        }
        return null;
    }

    @Override
    public Kategorie getKategorieByPath(String path) {
        System.out.println("get by path " + path);
        for (Kategorie k : kategorien) {
            if (k.getPath().equals(path)) {
                return k;
            }
        }
        return null;
    }

}
