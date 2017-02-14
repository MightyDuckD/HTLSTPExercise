/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lab06;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Simon
 */
@Named(value = "pdfcon")
@RequestScoped
public class PDFController implements Serializable {

    private String logo = "IF", name = "", filename = "", defaultFilename = "EmployeeReport.pdf";
    private String[] imgArray = {"IF", "EL", "ET", "MB", "WI"};

    public String getDefaultFilename() {
        return defaultFilename;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String[] getImgArray() {
        return imgArray;
    }

    public void setImgArray(String[] imgArray) {
        this.imgArray = imgArray;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @PostConstruct
    public void init() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PDFController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendPDF(ActionEvent e) throws ClassNotFoundException {
        ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();

        try (Connection con = DriverManager.getConnection("jdbc:postgresql://if-remote.htlstp.ac.at:54322/5A_10_Lab06", "5AHIF_16", "jeke18");
                InputStream is = ext.getResourceAsStream("/reports/employee_report.jasper");) {
            // Externen Context holen

            //Create ParameterMap
            Map<String, Object> parameter = new HashMap<>();
            ImageIcon i = new ImageIcon(ext.getRealPath("/images/HTL-" + logo + ".png"));
            parameter.put("image", i.getImage());
            parameter.put("name", name);

            //Choose a suitable filename
            String cleanFilename = filename.isEmpty() ? defaultFilename : filename;
            if (!cleanFilename.toLowerCase().endsWith(".pdf")) {
                cleanFilename += ".pdf";
            }

            // HttpServletResponse konfigurieren
            HttpServletResponse response = (HttpServletResponse) ext.getResponse();
            response.reset();
            response.setContentType("application/pdf");

            //append the .pdf extension to the filename if not already there
            response.setHeader("Content-Disposition", "attachment; filename=\"" + cleanFilename + "\"");

            OutputStream output = response.getOutputStream();
            JasperPrint jasperPrint = JasperFillManager.fillReport(is, parameter, con);

            // generate report and send it
            JasperExportManager.exportReportToPdfStream(jasperPrint, output);

        } catch (SQLException | JRException | IOException ex) {
            Logger.getLogger(PDFController.class.getName()).log(Level.SEVERE, "Fehler beim erstellen eines report", ex);
        }
        // cancel JSF Lifecycle
        FacesContext.getCurrentInstance().responseComplete();
    }
}
