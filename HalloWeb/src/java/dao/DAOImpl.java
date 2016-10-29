/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import model.Action;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import util.HibernateUtil;

/**
 *
 * @author 20120093
 */
public class DAOImpl implements DAO {

    @Override
    public boolean saveAction(Action a) {
        // Daten speichern
        Integer id = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            id = (Integer) session.save(a);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            return false;
        } finally {
            session.close();
        }
        return id != null;

    }

    @Override
    public List<Action> getAllActions() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createCriteria(Action.class).list();
        } catch (Exception exx) {
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Action> getAllActionsSorted(int limit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createCriteria(Action.class).addOrder(Order.desc("createDate")).setMaxResults(limit).list();
        } catch (Exception exx) {
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean dropAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = String.format("delete from %s", Action.class.getName());
            Query query = session.createQuery(hql);
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void open() {

    }

}
