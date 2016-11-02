/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author reio
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry = null;

    static {
        Runnable startHibernate = new Runnable() {
            @Override
            public void run() {
                try {
                    // loads configuration and mappings
                    Configuration configuration = new Configuration().configure();
                    serviceRegistry
                            = new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties()).build();

                    // builds a session factory from the service registry
                    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                } catch (HibernateException ex) {
                    // Log the exception. 
                    System.out.println("Initial SessionFactory creation failed." + ex);
                }
            }
        };
        startHibernate.run();
//        ExecutorService service = Executors.newFixedThreadPool(1);
//        Future f = service.submit(startHibernate);
//        try {
//            f.get(10, TimeUnit.SECONDS);
//        } catch (Exception ex) {
//            List<Runnable> shutdownNow = service.shutdownNow();
//            System.out.println("execution of hibernate startup timed out. falling back to static dao");
//            sessionFactory = null;
//            serviceRegistry = null;
//        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void close() {
        if (serviceRegistry != null) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }

    public static boolean isConnected() {
        return sessionFactory != null;
    }
}
