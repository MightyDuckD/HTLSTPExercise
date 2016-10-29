/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.DAO;
import dao.DAOImpl;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import model.Action;
import org.hibernate.SessionFactory;

/**
 *
 * @author 20120093
 */
@WebListener
public class HibernateServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DAO dao = new DAOImpl();
        dao.open();
        dao.saveAction(new Action(new Timestamp(System.currentTimeMillis()),"contextInitialized"));
        sce.getServletContext().setAttribute("dao", dao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DAO dao = (DAO) sce.getServletContext().getAttribute("dao");
        dao.saveAction(new Action(new Timestamp(System.currentTimeMillis()),"contextDestroyed"));
        try {
            dao.close();
        } catch (Exception ex) {
            Logger.getLogger(HibernateServletContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        HibernateUtil.close();
    }
    
}
