/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lehnerreisenconsole;

import at.mightyduck.lehnerreisenconsole.model.Benutzer;
import at.mightyduck.lehnerreisenconsole.model.Reisetyp;
import at.mightyduck.lehnerreisenconsole.model.Reiseveranstaltung;
import at.mightyduck.lehnerreisenconsole.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;

/**
 *
 * @author Simon
 */
public class DAO {

    public void open() {
        HibernateUtil.getSessionFactory();
    }

    public void close() {
        HibernateUtil.close();
    }

    public void save(Object o) {
        transaction((session) -> {
            session.save(o);
        });
    }

    public List<Benutzer> getBenutzer() {
        return transaction((session) -> {
            return new ArrayList<>(session.createCriteria(Benutzer.class).list());
        });
    }

    public List<Reisetyp> getReisetypen() {
        return transaction((session) -> {
            return new ArrayList<>(session.createCriteria(Reisetyp.class).list());
        });
    }

    public List<Reiseveranstaltung> getReiseveranstaltungen() {
        return transaction((session) -> {
            return new ArrayList<>(session.createCriteria(Reiseveranstaltung.class).list());
        });
    }

    private static void transaction(Consumer<Session> consumer) {
        transaction((session)-> {
            consumer.accept(session);
            return null;
        });
    }
    private static <T> T transaction(Function<Session, T> consumer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            T t = consumer.apply(session);
            transaction.commit();
            return t;
        } catch (Exception ex) {
            transaction.rollback();
            Logger.getLogger(DAO.class.getName()).error("Transaction failed", ex);
            return null;
        } finally {
            session.close();
        }
    }
}
