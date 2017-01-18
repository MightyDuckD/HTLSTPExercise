/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lehnerreisenweb.controller;

import at.mightyduck.lehnerreisenconsole.DAO;
import at.mightyduck.lehnerreisenconsole.model.Benutzer;
import at.mightyduck.lehnerreisenconsole.model.Reisetyp;
import at.mightyduck.lehnerreisenconsole.util.UserUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Simon
 */
@Named("usercon")
@SessionScoped
public class UserController implements Serializable {

    @Inject
    private DAO dao;

    private List<Reisetyp> selectedReisetypen;
    private Benutzer aktuellerBenutzer;
    private String email = "simon@mightyduck.at";
    private String password = "password";

    private String reg_email;
    private String reg_password1;
    private String reg_password2;

    public Benutzer getBenutzer() {
        return aktuellerBenutzer;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        System.out.println("pw set: " + password);
        this.password = password;
    }

    public void setEmail(String email) {
        System.out.println("em set: " + email);
        this.email = email;
    }

    public DAO getDao() {
        return dao;
    }

    public void setSelectedReisetypen(List<Reisetyp> selectedReisetypen) {
        System.out.println(selectedReisetypen);
        this.selectedReisetypen = selectedReisetypen;
    }

    public List<Reisetyp> getSelectedReisetypen() {
        selectedReisetypen = new ArrayList<>(aktuellerBenutzer.getInteressen());
        return selectedReisetypen;
    }

    public void saveSelectedReisetypen() {
        dao.setReisetypen(aktuellerBenutzer, selectedReisetypen);
    }

    public void login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();

        System.out.println(email);
        System.out.println(password);
        try {
            System.out.println("get user by email 1 " + email);
            Benutzer benutzerTest = dao.getBenutzerByEmail(email);
            if (UserUtil.authenticateUser(benutzerTest, email, password)) {
                aktuellerBenutzer = benutzerTest;
                System.out.println("logged in");
                ec.redirect("member/member.xhtml");
                return;
            }
            context.addMessage("loginform", new FacesMessage("Email/Passwort Kombination ungültig"));
        } finally {
            password = null;
        }
    }

    public void register() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println(reg_password1 + " " + reg_password2);
        boolean failed = false;
        if (!Objects.equals(reg_password1, reg_password2)) {
            context.addMessage("registerform:reg_password2", new FacesMessage("The passwords do not match."));
            failed = true;
        }
        if (dao.getBenutzerByEmail(reg_email) != null) {
            context.addMessage("id registerform:reg_email", new FacesMessage("The email adress is already in use."));
            failed = true;
        }
        if (!failed) {
            Benutzer user = UserUtil.createUser(reg_email, reg_password1);
            dao.save(user);
            aktuellerBenutzer = user;
            context.getExternalContext().redirect("member/member.xhtml");
        }
    }

    public String logout() throws IOException {
        dao.update(aktuellerBenutzer);
        email = password = null;
        aktuellerBenutzer = null;
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("hello.xhtml");
        return "hello.xhtml";
    }

    public void setReg_email(String reg_email) {
        this.reg_email = reg_email;
    }

    public void setReg_password1(String reg_password1) {
        this.reg_password1 = reg_password1;
    }

    public void setReg_password2(String reg_password2) {
        this.reg_password2 = reg_password2;
    }

    public String getReg_email() {
        return reg_email;
    }

    public String getReg_password1() {
        return reg_password1;
    }

    public String getReg_password2() {
        return reg_password2;
    }

}
