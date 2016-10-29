/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.DAO;
import java.sql.Timestamp;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequest;
import model.Action;

/**
 *
 * @author Simon
 */
public class LoggingUtil {
    public static boolean log(ServletConfig config,String message) {
        return log(config.getServletContext(),message);
    }
    public static boolean log(ServletRequest req,String message) {
        return log(req.getServletContext(),message);
    }
    public static boolean log(ServletContextEvent event,String message) {
        return log(event.getServletContext(),message);
    }
    public static boolean log(ServletContext context,String message) {
        DAO dao = (DAO) context.getAttribute("dao");
        return dao.saveAction(new Action(new Timestamp(System.currentTimeMillis()),message));
    }
}
