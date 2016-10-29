/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNIPARZ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author 2012
 */
public class SNIPARZ {
    public static void main(String[] args) throws URISyntaxException, MalformedURLException, IOException, InterruptedException {
        JDialog frame = new JDialog();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int w = 1280;
        int h = 720;
        frame.setLocation(width-w,height-h);
        frame.setSize(w,h);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setOpacity(1);
        frame.setAlwaysOnTop(true);
        URL url = SNIPARZ.class.getResource("quickscope.gif");
        Icon myImgIcon = new ImageIcon(url);
        JLabel imageLbl = new JLabel(myImgIcon);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.getContentPane().add(imageLbl,BorderLayout.CENTER);
        Runtime r = Runtime.getRuntime();
        while(true) {
            frame.setVisible(true);
            Process p = r.exec("SnippingTool.exe");
            p.waitFor();
        }
    }
}
