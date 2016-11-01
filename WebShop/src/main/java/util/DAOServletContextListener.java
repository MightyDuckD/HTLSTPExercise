/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.DAO;
import dao.StaticDAO;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Simon
 */
@WebListener
public class DAOServletContextListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DAO dao = new StaticDAO();
        dao.open(sce.getServletContext());
        sce.getServletContext().setAttribute(AttributeUtil.DAO, dao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DAO dao = (DAO) sce.getServletContext().getAttribute(AttributeUtil.DAO);
        dao.close();
    }
    
}
