/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import model.Artikel;
import model.Category;
import model.Users;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author Simon
 */
public class HibernateDAO implements DAO {

    @Override
    public Users getUserByUsername(String name) {
        return (Users) getById(Users.class, name);
    }

    @Override
    public List<Artikel> getAllArtikel() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createCriteria(Artikel.class).list();
        } catch (Exception exx) {
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Artikel> getAllArtikelByKategorie(Category k) {
        //TODO: database filtering
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            List<Artikel> artikel = new ArrayList<>();
            for (Artikel a : getAllArtikel()) {
                session.saveOrUpdate(a);
                for (Category ak : a.getCategories()) {
                    while (ak != null) {
                        if (ak.getId() == k.getId()) {
                            artikel.add(a);
                            break;
                        }
                        ak = ak.getParent();
                    }
                }
            }
            tx.commit();
            return artikel;
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public Artikel getArtikelById(int id) {
        return (Artikel) getById(Artikel.class, id);
    }

    @Override
    public List<Category> getAllCategorien() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createCriteria(Category.class).list();
        } catch (Exception exx) {
            return null;
        } finally {
            session.close();
        }
    }

    private <T> T getById(Class<?> clas, Serializable id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return (T) session.get(clas, id);
        } catch (Exception e) {
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Category getCategoryById(int id) {
        return (Category) getById(Category.class, id);
    }

    @Override
    public List<Category> getChildrenById(int id) {
        List<Category> result = new ArrayList<>();//TODO: bessere datenbank zugriffe
        for (Category k : getAllCategorien()) {
            if (k.getParent() != null && k.getParent().getId() == id) {
                result.add(k);
            }
        }
        return result;
    }

    @Override
    public void close() {
        HibernateUtil.getSessionFactory().close();
    }

    @Override
    public void open(ServletContext servletContext) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
    }

    @Override
    public Category getKategorieByPath(String path) {
        for (Category k : getAllCategorien()) {
            if (k.getPath().equals(path)) {
                return k;
            }
        }
        return null;
    }

    @Override
    public void dumpToJson(OutputStream output) {

    }

    @Override
    public void readFromJson(InputStream input) {

    }

    private void save(Object o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(o);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void addArtikel(Artikel artikel) {
        save(artikel);
    }

    @Override
    public void addCategory(Category category) {
        save(category);
    }

    @Override
    public void addUser(Users user) {
        save(user);
    }

    @Override
    public void persist(Object o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(o);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            System.out.println(ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    

    
}
