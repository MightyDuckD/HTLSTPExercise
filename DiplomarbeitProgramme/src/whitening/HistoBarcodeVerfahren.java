/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whitening;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Simon
 */
public class HistoBarcodeVerfahren {

    public static interface TriConsumer<A, B, C> {

        public void consume(A a, B b, C c);
    }

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

    public static void forEach(BufferedImage img, TriConsumer<Integer, Integer, Integer> consumer) {
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                consumer.consume(x, y, img.getRGB(x, y));
            }
        }
    }

    public static class Coord {

        public final int x;
        public final int y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    /**
     * 2x stands for 2 pixel per bar
     *
     * @param img
     */
    public static boolean[] loadBarcode2x(BufferedImage img) {
        boolean result[] = new boolean[img.getWidth() / 2];
        for (int i = 0; i < img.getWidth(); i += 2) {
            result[i / 2] = avg(img.getRGB(i, 0)) > 128;
        }
        return result;
    }

    public static int transform(int color, int avg) {
//        int in = color;
        while (avg(color) > avg) {
            color = rgb(r(color) - 1, g(color) - 1, b(color) - 1);
        }
        while (avg(color) < avg) {
            color = rgb(r(color) + 1, g(color) + 1, b(color) + 1);
        }
//        System.out.println("in:"+str(in) + " out:" + str(color));
        return color;
    }

    public static void main(String[] args) throws IOException {
        //
        BufferedImage img = ImageIO.read(new File("earth.png"));
        BufferedImage bar = ImageIO.read(new File("barcode-pure-2x.png"));
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        //
        int histo[] = new int[256];
        List<Coord> coords[] = new ArrayList[256];
        for (int i = 0; i < coords.length; i++) {
            coords[i] = new ArrayList<>();
        }
        //
        forEach(img, (x, y, c) -> {
            c = avg(c);
            histo[c]++;
            coords[c].add(new Coord(x, y));
        });
        //
        boolean[] barcode = loadBarcode2x(bar);
        System.out.println(Arrays.toString(histo));
        int pos = 0;
        int neg = 0;
        for (boolean a : barcode) {
            System.out.print(a ? ' ' : '#');
            if (a) {
                pos++;
            } else {
                neg++;
            }
        }
        System.out.println("");
        System.out.println(pos + " " + neg);
        System.out.println(barcode.length + " " + histo.length);
        int last = 0;
        for (int i = 0; i < 256; i++) {
//            System.out.println("doing " + i + " " + last);
            for (int j = 0; j < coords[i].size(); j++) {
                Coord c = coords[i].get(j);
                int color = img.getRGB(c.x, c.y);
                if (i < barcode.length && barcode[i]) {
                    color = transform(color, last
                    );
                } else {
                    last = i;
                }
                result.setRGB(c.x, c.y, color);
            }
        }
        ImageIO.write(result, "png", new File("barcode-earth-output.png"));

        int histo2[] = new int[256];

        forEach(result, (x, y, c) -> {
            histo2[avg(c)]++;
        });
        ImageIO.write(render(histo2, 0, 300), "png", new File("barcode-earth-histo.png"));
        System.out.println(Arrays.toString(histo2));

    }

    public static BufferedImage render(int histo[], int low, int high) {
        int height = high - low + 1;
        BufferedImage img = new BufferedImage(histo.length, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < histo.length; i++) {
            int from = 0;
            int to = Math.min(height - 1, histo[i] - low);
            for (int j = from; j < to; j++) {
                img.setRGB(i, j, Color.black.getRGB());
            }
            for (int j = to; j < height; j++) {
                img.setRGB(i, j, Color.white.getRGB());
            }
        }
        return img;
    }

}
