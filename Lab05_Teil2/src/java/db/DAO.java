/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import eager.Eager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import model.Hobby;
import model.Person;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Simon
 */
@Named
//@Eager //not working for some reason (the service is not added in META-INF)
//easier fix for slow first request -> omnifaces -> @Eager
@ApplicationScoped
public class DAO {

    @PostConstruct
    public void init() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        try {
            s.save(new Hobby("Zeichnen"));
            s.save(new Hobby("Lesen"));
            s.save(new Hobby("Schwimmen"));
            s.save(new Hobby("Fernsehen"));
            s.save(new Hobby("Radfahren"));
            tx.commit();
        } catch (HibernateException he) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Datenbank konnte nicht initialisiert werden", he);
            tx.rollback();
        } finally {
            s.close();
        }
    }

    @PreDestroy
    public void destroy() {
        HibernateUtil.close();
    }

    public List<Hobby> getHobbies() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        List<Hobby> result = Collections.EMPTY_LIST;
        try {
            result = s.createQuery("FROM Hobby").list();
            tx.commit();
        } catch (HibernateException he) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Hobbies konnten nicht aus Datenbank geladen werden", he);
            tx.rollback();
        } finally {
            s.close();
        }
        return result;
    }

    public List<Person> getPersonen() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        List<Person> result = Collections.EMPTY_LIST;
        try {
            result = s.createQuery("FROM Person").list();
            tx.commit();
        } catch (HibernateException he) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Personen konnten nicht aus Datenbank geladen werden", he);
            tx.rollback();
        } finally {
            s.close();
        }
        return result;
    }

    public boolean savePerson(Person p) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        try {
            s.save(p);
            tx.commit();
            return true;
        } catch (HibernateException he) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Person konnte nicht in Datenbank gespeichert werden", he);
            tx.rollback();
            return false;
        } finally {
            s.close();
        }
    }

}
