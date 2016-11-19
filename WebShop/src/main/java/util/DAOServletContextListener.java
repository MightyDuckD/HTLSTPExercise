/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.DAO;
import dao.HibernateDAO;
import dao.StaticDAO;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import model.Artikel;
import model.Category;
import model.Prize;

/**
 *
 * @author Simon
 */
@WebListener
public class DAOServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DAO dao = new StaticDAO();
//        if(HibernateUtil.isConnected())
//            dao = new HibernateDAO();
        dao.open(sce.getServletContext());
        sce.getServletContext().setAttribute(AttributeUtil.DAO, dao);

        dao.addUser(UserUtil.create("user01", "pwd01"));
        dao.addUser(UserUtil.create("user02", "pwd02"));
        dao.addUser(UserUtil.create("user03", "pwd03"));
        dao.addUser(UserUtil.create("user04", "pwd04"));
        dao.addUser(UserUtil.create("user05", "pwd05"));

        dao.addCategory(new Category(0, "New", "", null));

        dao.addCategory(new Category(1, "Clothing", "Clothing", null));
        dao.addCategory(new Category(11, "T-Shirts", "Clothing/TShirt", dao.getCategoryById(1)));
        dao.addCategory(new Category(12, "Hoodies", "Clothing/Hoodies", dao.getCategoryById(1)));
        dao.addCategory(new Category(13, "Jackets &amp Outerwear", "Clothing/JacketsOuterwear", dao.getCategoryById(1)));

        dao.addCategory(new Category(2, "Toys &amp; Games", "ToysGames", null));
        dao.addCategory(new Category(21, "Puzzles", "ToysGames/Puzzles", dao.getCategoryById(2)));
        dao.addCategory(new Category(22, "DIY &amp; Sciencce Toys", "ToysGames/DIYScienceToys", dao.getCategoryById(2)));

        dao.addCategory(new Category(3, "Home &amp; Office", "HomeOffice", null));
        dao.addCategory(new Category(31, "Blankets, Rugs &amp; Towels", "HomeOffice/BlanketsRugsTowels", dao.getCategoryById(3)));
        dao.addCategory(new Category(32, "Coffee Mugs &amp; Travel Mugs", "HomeOffice/CoffeeMugsTravelMugs", dao.getCategoryById(3)));
        dao.addCategory(new Category(33, "Glassware &amp; Drinkware", "HomeOffice/GlasswareDrinkware", dao.getCategoryById(3)));

        dao.addCategory(new Category(4, "On Sale", "Sale", null));

        dao.addArtikel(new Artikel(1, "Caffeine Mug 1", new Prize(799, 0.5), dao.getCategoryById(32), dao.getCategoryById(0)));
        dao.addArtikel(new Artikel(2, "Caffeine Mug 2", new Prize(799, 0.5), dao.getCategoryById(32), dao.getCategoryById(0)));
        dao.addArtikel(new Artikel(3, "Caffeine Mug 3", new Prize(799, 0.5), dao.getCategoryById(32), dao.getCategoryById(0)));
        dao.addArtikel(new Artikel(1, "Caffeine Mug 4", new Prize(799, 0.5), dao.getCategoryById(32), dao.getCategoryById(0)));
        dao.addArtikel(new Artikel(2, "Caffeine Mug 5", new Prize(799, 0.5), dao.getCategoryById(32), dao.getCategoryById(0)));
        dao.addArtikel(new Artikel(3, "Caffeine Mug 6", new Prize(799, 0.5), dao.getCategoryById(32), dao.getCategoryById(0)));
        dao.addArtikel(new Artikel(1, "Caffeine Mug 7", new Prize(799, 0.5), dao.getCategoryById(32), dao.getCategoryById(0)));
        dao.addArtikel(new Artikel(2, "Caffeine Mug 8", new Prize(799, 0.5), dao.getCategoryById(32), dao.getCategoryById(0)));
        dao.addArtikel(new Artikel(3, "Caffeine Mug 9", new Prize(799, 0.5), dao.getCategoryById(32), dao.getCategoryById(0)));
        dao.addArtikel(new Artikel(4, "Caffeine Mug 0", "short", "long", "icon", "image", new Prize(799, 0.5), dao.getCategoryById(32)));

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DAO dao = (DAO) sce.getServletContext().getAttribute(AttributeUtil.DAO);
        dao.close(); 
    }

}
