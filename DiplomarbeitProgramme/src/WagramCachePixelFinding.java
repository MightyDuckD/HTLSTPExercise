
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Simon
 */
public class WagramCachePixelFinding {

    public static void main(String[] args) throws MalformedURLException, IOException {
        BufferedImage image = ImageIO.read(new URL("http://localhost:8080/geoui/res/wagram.jpg"));

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int rgb = image.getRGB(i, j);
                Color col = new Color(rgb);
                int c = 0;
                if(col.getRed() == c && col.getGreen() == c && col.getBlue() == c)
                    System.out.printf("found at %d %d\n",i,j);
            }
        }

    }
}
