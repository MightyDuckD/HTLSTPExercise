/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author Simon
 */
public class GenerateImage {

    public static Random rand = new Random();

    public static BufferedImage generate(int w, int h, String txt) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        int x = 10;
        int y = 50;
        g2d.setColor(Color.white);
        g2d.scale(w / 68.0, h / 68.0);
        g2d.fillRect(0, 0, 136, 68);
        for (char c : txt.toCharArray()) {
            String font = rand.nextBoolean() ? "Serif" : "Arial";
            g2d.setFont(new Font(font, Font.BOLD, 40));
            FontMetrics fm = g2d.getFontMetrics();
            g2d.setPaint(Color.gray);
            g2d.drawString("" + c, x + 1, y + 1);
            g2d.setPaint(Color.getHSBColor(rand.nextFloat(), 0.5f, 0.8f));
            g2d.drawString("" + c, x, y);
            x += fm.stringWidth("" + c) - rand.nextDouble() * 6 + 3;
        }
        g2d.dispose();
        return img;
    }

    private static int get(BufferedImage src, double x, double y) {
        if (x < 0 || x >= src.getWidth()) {
            return Color.white.getRGB();
        }
        if (y < 0 || y >= src.getHeight()) {
            return Color.white.getRGB();
        }
        return src.getRGB((int) x, (int) y);
    }

    public static BufferedImage transform(int w, int h, String txt) {
        BufferedImage img1 = generate(w * 2, h * 2, txt);
        BufferedImage img2 = new BufferedImage(w, h, img1.getType());
        double phase = rand.nextDouble() * Math.PI * 2;
        for (int i = 0; i < img2.getWidth(); i++) {
            for (int j = 0; j < img2.getHeight(); j++) {
                double x = i + Math.sin(phase + 0.04 * i) * 10;
                double y = j + Math.sin(phase + 0.1f * i) * 8 + 8;
                img2.setRGB(i, j, get(img1, x * 2, y * 2));
            }
        }

        return img2;
    }
}
