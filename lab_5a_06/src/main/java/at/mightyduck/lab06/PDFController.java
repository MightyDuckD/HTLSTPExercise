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
@Named(value = "pdfController")
@RequestScoped
public class PDFController implements Serializable {

    private String logo = "IF";
    private String[] imgArray = {"IF","EL","ET","MB","WI"};

    public String getPattern() {
        return logo;
    }

    public void setPattern(String pattern) {
        this.logo = pattern;
    }

    public String[] getImgArray() {
        return imgArray;
    }

    public void setImgArray(String[] imgArray) {
        this.imgArray = imgArray;
    }

    public void sendPDF(ActionEvent e) throws ClassNotFoundException {
        JasperPrint jasperPrint;
        Class.forName("org.postgresql.Driver");

        try (Connection con = DriverManager.getConnection("jdbc:postgresql://193.170.118.29:54322/5A_18_iReport_employee", "5AHIF_16", "jeke18")) {
            // Externen Context holen
            ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();

            // ParameterMap definieren
            Map<String, Object> parameter = new HashMap<>();
            ImageIcon i = new ImageIcon(ext.getRealPath("/images/HTL-" + logo +".png"));
            parameter.put("image", i.getImage());
            String name = "HTL-" + logo;
            parameter.put("name", name);

            // HttpServletResponse konfigurieren
            HttpServletResponse response = (HttpServletResponse) ext.getResponse();
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"Employee_Report.pdf\"");

            try (InputStream is = ext.getResourceAsStream("/reports/employee_report.jasper");
                    OutputStream output = response.getOutputStream()) {
                jasperPrint = JasperFillManager.fillReport(is, parameter, con);
                System.out.println("Report erfolgreich befüllt");

                // Report in den SerrvletOutputstream exportieren
                JasperExportManager.exportReportToPdfStream(jasperPrint, output);
                System.out.println("Report erfolgreich gesendet");
            } catch (JRException | IOException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // JSF Lebenszyklus abbrechen
        FacesContext.getCurrentInstance().responseComplete();
    }
}
