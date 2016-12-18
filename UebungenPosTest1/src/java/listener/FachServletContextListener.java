/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import model.Faecher;

/**
 *
 * @author Simon
 */
@WebListener
public class FachServletContextListener implements javax.servlet.ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("faecher", new Faecher());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().log(sce.getServletContext().getAttribute("faecher").toString());
    }
    
}
