
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
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
public class ConvolveOpExample {

    public static BufferedImage convert(BufferedImage input, int type) {
        BufferedImage copy = new BufferedImage(input.getWidth(), input.getHeight(), type);
        Graphics2D g2d = copy.createGraphics();
        g2d.drawImage(input, 0, 0, null);
        g2d.dispose();
        return copy;
    }

    public static void apply3x3(float[] kernel, BufferedImage input,BufferedImage dest) throws IOException {
        ConvolveOp op = new ConvolveOp(new Kernel(3, 3, kernel));
        BufferedImage out1 = op.filter(input, dest);
    }

    public static void pinsel(int radius, Color color, BufferedImage source, BufferedImage dest) {
        Graphics2D ctx = dest.createGraphics();
        ctx.setColor(color);
        for (int i = 0; i < dest.getWidth(); i++) {
            for (int j = 0; j < dest.getHeight(); j++) {
                if ((source.getRGB(i, j) & 0xFF) > 127) {
                    ctx.fillOval(i - radius, j - radius, 2 * radius - 1, 2 * radius - 1);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        float[] laplacian8 = {1f, 1f, 1f, 1f, -8f, 1f, 1f, 1f, 1f};
        
        BufferedImage mask = convert(ImageIO.read(new File("mask.png")), BufferedImage.TYPE_INT_RGB);
        BufferedImage mask1 = new BufferedImage(mask.getWidth(), mask.getHeight(), mask.getType());
        BufferedImage mask2 = new BufferedImage(mask.getWidth(), mask.getHeight(), mask.getType());
        BufferedImage mask3 = new BufferedImage(mask.getWidth(), mask.getHeight(), mask.getType());
        BufferedImage result = convert(mask, mask.getType());
        
        pinsel(10, Color.white, mask, mask1);
        apply3x3(laplacian8, mask1,mask2);
        pinsel(5, Color.red, mask2,mask3);
        pinsel(5, Color.red, mask2,result);
        
        ImageIO.write(mask1, "png", new File("mask1-pinsel10.png"));
        ImageIO.write(mask2, "png", new File("mask2-edges.png"));
        ImageIO.write(mask3, "png", new File("mask3-pinsel5.png"));
        ImageIO.write(result, "png", new File("mask4-result.png"));
    }
}
