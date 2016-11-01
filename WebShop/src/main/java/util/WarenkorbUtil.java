/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.DAO;
import javax.servlet.http.HttpServletRequest;
import model.User;
import model.Warenkorb;

/**
 *
 * @author Simon
 */
public class WarenkorbUtil {

    /**
     * Session or User Warenkorb switch + create if not exists.
     *
     * @param request The Warenkorb item which should be used.
     * @return
     */
    public static Warenkorb getWarenkorb(HttpServletRequest request) {//TODO: move to location suitable for getwarenkorb
        DAO dao = (DAO) request.getServletContext().getAttribute(AttributeUtil.DAO);
        String username = (String) request.getSession().getAttribute(AttributeUtil.USER);
        if (username != null) {
            User user = dao.getUserByUsername(username);
            Warenkorb korb = user.getWarenkorb();
            if (korb == null) {
                korb = new Warenkorb();
                user.setWarenkorb(korb);
            }
            return korb;
        }
        Warenkorb korb = (Warenkorb) request.getSession().getAttribute(AttributeUtil.WARENKORB);
        if (korb == null) {
            korb = new Warenkorb();
            request.getSession().setAttribute(AttributeUtil.WARENKORB, korb);
        }
        return korb;
    }

    public static String getWarenkorbSource(HttpServletRequest request) {
        if (request.getSession().getAttribute(AttributeUtil.USER) == null) {
            return "Session";
        }
        return "User";
    }

    public static void moveWarenkorbFromSession(HttpServletRequest request) {
        DAO dao = (DAO) request.getServletContext().getAttribute(AttributeUtil.DAO);
        String username = (String) request.getSession().getAttribute(AttributeUtil.USER);
        Warenkorb korb = (Warenkorb) request.getSession().getAttribute(AttributeUtil.WARENKORB);
        User user = dao.getUserByUsername(username);
        if(korb != null && user.getWarenkorb() == null) {
            user.setWarenkorb(korb);
            request.getSession().removeAttribute(AttributeUtil.WARENKORB);
        }
    }
}
