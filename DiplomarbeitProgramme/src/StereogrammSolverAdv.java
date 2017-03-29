
import blend.BlendComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.jtransforms.fft.DoubleFFT_1D;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Simon
 */
public class StereogrammSolverAdv {

    public static int r(int c) {
        return (c >> 16) & 0xFF;
    }

    public static int g(int c) {
        return (c >> 8) & 0xFF;
    }

    public static int b(int c) {
        return (c >> 0) & 0xFF;
    }

    public static int avg(int c) {
        return (r(c) + g(c) + b(c)) / 3;
    }

    public static int cut(int a) {
        return Math.min(255, Math.max(0, a));
    }

    public static String str(int c) {
        return String.format("[%d,%d,%d]", r(c), g(c), b(c));
    }

    public static int rgb(int r, int g, int b) {
        return 0xff000000 | (((cut(r) & 0XFF) << 16) | ((cut(g) & 0xFF) << 8) | ((cut(b) & 0xFF) << 0));
    }

    private static int diff(int c1, int c2) {
        return rgb(
                Math.abs(r(c1) - r(c2)),
                Math.abs(g(c1) - g(c2)),
                Math.abs(b(c1) - b(c2))
        );
    }

    public static BufferedImage diff(BufferedImage source, int offset) {
        BufferedImage img = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D ctx = img.createGraphics();
        ctx.drawImage(source, 0, 0, null);
        ctx.setComposite(BlendComposite.Difference);
        ctx.drawImage(source, offset, 0, null);

        ctx.dispose();
        return img;
    }

    public static double test(BufferedImage src, int treshold, int offset) {
        int cnt = 0;
        for (int x = offset; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                cnt += (avg(src.getRGB(x, y)) <= treshold) ? 1 : 0;
            }
        }
        return (double) cnt / (src.getHeight() * (src.getWidth() - offset));
    }

    public static void main(String[] args) throws Exception {//''
//        BufferedImage original = ImageIO.read(new URL("http://localhost:8080/geoui/res/text.png"));
//        BufferedImage original = ImageIO.read(new URL("http://localhost:8080/geoui/res/heart.png"));
//        BufferedImage original = ImageIO.read(new URL("http://localhost:8080/geoui/res/wagram-bigger.png"));
//        BufferedImage original = ImageIO.read(new URL("http://localhost:8080/geoui/res/ost-smaller.jpg"));
        BufferedImage original = ImageIO.read(new URL("http://localhost:8080/geoui/res/what.png"));
        //resize image for performance reasons
        BufferedImage convertedImg = new BufferedImage(400, (int) (400.0 * original.getHeight() / original.getWidth()), BufferedImage.TYPE_INT_RGB);
        convertedImg.getGraphics().drawImage(original, 0, 0, convertedImg.getWidth(), convertedImg.getHeight(), null);
        //
        int length = convertedImg.getWidth();
        System.out.println("done with ");
        double data[] = new double[length];
        for (int i = 0; i < length; i++) {
            BufferedImage result = diff(convertedImg, i);
            data[i] = (float) test(result, 20, i);
            System.out.println(data[i]);
        }

        System.out.println("Biggest diff between two points = " + getBiggestDiff(data));

    }

    public static double getBiggestDiff(double[] data) {
        double mn = data[0];
        double biggest = Double.MIN_VALUE;
//        for (int i = 0; i < data.length * 3 / 4; i++) {
        for (int i = 0; i < data.length; i++) {
            biggest = Math.max(biggest, data[i] - mn);
            mn = Math.min(mn, data[i]);
        }
        return biggest;
    }

}
