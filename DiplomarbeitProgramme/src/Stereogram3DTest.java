
import blend.BlendComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
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
public class Stereogram3DTest {

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

    public static BufferedImage convert(BufferedImage input, int type) {
        BufferedImage copy = new BufferedImage(input.getWidth(), input.getHeight(), type);
        Graphics2D g2d = copy.createGraphics();
        g2d.drawImage(input, 0, 0, null);
        g2d.dispose();
        return copy;
    }

    public static float[] scale(float[] data, float factor) {
        for (int i = 0; i < data.length; i++) {
            data[i] *= factor;
        }
        return data;
    }

    public static BufferedImage avgbox(BufferedImage input, int size) {
        float data[] = new float[size * size];
        for (int i = 0; i < data.length; i++) {
            data[i] = 1f / (size * size);
        }
        return new ConvolveOp(new Kernel(size, size, data)).filter(input, null);
    }

    public static BufferedImage gaussian(BufferedImage input) {
        float data[] = scale(new float[]{
            1, 4, 6, 4, 1,
            4, 16, 24, 16, 4,
            6, 24, 36, 34, 6,
            4, 16, 24, 16, 4,
            1, 4, 6, 4, 1
        }, 1 / 256.0f);
        return new ConvolveOp(new Kernel(5, 5, data)).filter(input, null);
    }

    public static BufferedImage oilpainting(BufferedImage input, int levelcount, int rad) {
        BufferedImage result = convert(input, input.getType());
        int[][] level = new int[input.getWidth()][input.getHeight()];
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                level[x][y] = (avg(input.getRGB(x, y)) * levelcount / 255);
            }
        }

        for (int x = rad; x < input.getWidth() - rad; x++) {
            for (int y = rad; y < input.getHeight() - rad; y++) {
                int[] r = new int[levelcount+1];
                int[] g = new int[levelcount+1];
                int[] b = new int[levelcount+1];
                int[] cnt = new int[levelcount+1];
                for (int i = -rad; i <= rad; i++) {
                    for (int j = -rad; j <= rad; j++) {
                        int current = level[x + i][y + j];
                        int color = input.getRGB(x + i, y + j);
                        r[current] += r(color);
                        g[current] += g(color);
                        b[current] += b(color);
                        cnt[current]++;
                    }
                }
                int maxi = 0;
                for (int i = 0; i <= levelcount; i++) {
                    if (cnt[maxi] < cnt[i]) {
                        maxi = i;
                    }
                }
                result.setRGB(x, y, rgb(
                        r[maxi] / cnt[maxi],
                        g[maxi] / cnt[maxi],
                        b[maxi] / cnt[maxi]
                ));
            }
        }

        return result;
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

    
    public static void doit(BufferedImage original, BufferedImage result,int min, int max, int tresh) {
        for (int i = max; i >= min; i--) {
//        for (int i = min; i <= max; i++) {
            BufferedImage other = gaussian(gaussian(diff(original, i)));
//            BufferedImage other = gaussian(diff(original, i));
//            BufferedImage other = diff(original, i);
            Color color = new Color((float) (i - min) / (max - min),0,0);//, 0.3f, 0.3f);
            for (int x = i; x < result.getWidth(); x++) {
                for (int y = 0; y < result.getHeight(); y++) {
                    if (avg(other.getRGB(x, y)) < tresh) {
                        result.setRGB(x, y, color.getRGB());
                    }
                }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        
        
//        BufferedImage original = convert(ImageIO.read(new URL("http://localhost:8080/geoui/res/test.jpg")), BufferedImage.TYPE_INT_RGB);
//        BufferedImage original = convert(ImageIO.read(new URL("http://localhost:8080/geoui/res/stereo.jpg")), BufferedImage.TYPE_INT_RGB);
//        int max = 100;
//        int min = 60;
//        int max = 120;
//        int min = 80;
        
//        BufferedImage original = convert(ImageIO.read(new URL("http://localhost:8080/geoui/res/what.png")), BufferedImage.TYPE_INT_RGB);
//        BufferedImage original = convert(ImageIO.read(new URL("http://localhost:8080/geoui/res/city.jpg")), BufferedImage.TYPE_INT_RGB);
        BufferedImage original = convert(ImageIO.read(new URL("http://localhost:8080/geoui/res/large.gif")), BufferedImage.TYPE_INT_RGB);
        BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
//        ImageIO.write(oilpainting(original, 20,3), "png", new File("output-3d-oil.png"));
        
//        int max = 280;
//        int min = 240;
//        int max = 630;
//        int min = 530;
        int max = 170;
        int min = 80;
//        int max = 300;
//        int min = 200;
//        int max = 560;
//        int min = 320;
        System.out.println("stage 1");
        doit(original, result,min,max,20);
        System.out.println("stage 2");
        doit(original, result,min,max,5);
        System.out.println("stage 3");
        doit(original, result,min,max,2);
        System.out.println("stage 4");
        doit(original, result,min,max,1);
        System.out.println("saving");

        
        ImageIO.write(result, "png", new File("output-3d.png"));
        ImageIO.write(oilpainting(gaussian(result), 60,2), "png", new File("output-3d-oil.png"));
    }
}
