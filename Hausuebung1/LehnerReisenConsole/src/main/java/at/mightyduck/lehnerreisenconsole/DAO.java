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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;
/**
 *
 * @author Simon
 */
@ApplicationScoped
public class DAO implements Serializable{

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
    public void persist(Object o) {
        transaction((session) -> {
            session.persist(o);
        });
    }
    public void update(Object o) {
        transaction((session) -> {
            session.update(o);
        });
    }

    public Benutzer getBenutzerByEmail(String email) {
        System.out.println("get user by email " + email);
        return transaction((session) -> {
            Criteria criteria = session.createCriteria(Benutzer.class);
            criteria.add(Restrictions.eq("email", email));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Benutzer> users = criteria.list();
            System.out.println(users);
            if(users.isEmpty())
                return null;
            if(users.size() > 1)
                throw new RuntimeException("query by id should only return 1 element");
            return users.get(0);
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
        transaction((session) -> {
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
            System.out.println("exception " + ex);
            Logger.getLogger(DAO.class.getName()).error("Transaction failed", ex);
            return null;
        } finally {
            session.close();
        }
    }

    public void setReisetypen(Benutzer benutzer, Collection<Reisetyp> selectedReisetypen) {
        transaction((session)-> {
            session.update(benutzer);
            Set<Reisetyp> interessen = benutzer.getInteressen();
            for(Reisetyp typ : selectedReisetypen){
                interessen.add(typ);
            }
            benutzer.setInteressen(new HashSet<>(selectedReisetypen));
        });
    }

    public Set<Reiseveranstaltung> getVeranstaltungen(Reisetyp typ) {
        return transaction((session)-> {
            session.update(typ);
            return new HashSet<>(typ.getVeranstaltungen());
        });
    }

}
