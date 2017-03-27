
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import javax.imageio.ImageIO;

/**
 *
 * @author Simon
 */
public class HistogramGenerator {

    public static int r(int c) {
        return (c >> 16) & 0xFF;
    }

    public static int g(int c) {
        return (c >> 8) & 0xFF;
    }

    public static int b(int c) {
        return (c) & 0xFF;
    }

    public static int avg(int c) {
        return (r(c) + g(c) + b(c)) / 3;
    }

    public static String str(int c) {
        return String.format("[%d,%d,%d]", r(c), g(c), b(c));
    }

    public static void forEach(BufferedImage img, Consumer<Integer> consumer) {
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                consumer.accept(img.getRGB(x, y));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("java HistogramGenerator [inputfile] [outputfile]");
            return;
        }
        File in = new File(args[0]);
        File out = new File(args[1]);
        if (out.isDirectory() || in.isDirectory()) {
            System.out.println("Bitte zwei gültige Dateinamen angeben");
            return;
        }
        if (!in.exists()) {
            System.out.println("Datei " + in + " konnte nicht gefunden werden");
            return;
        }
        if (out.exists()) {
            System.out.println("Datei " + out + " existiert bereits");
            return;
        }
        BufferedImage source = ImageIO.read(in);
        int[] histogram = new int[256];
        int[] stat = new int[]{Integer.MIN_VALUE};
        forEach(source, (color) -> {
            int grey = avg(color);
            histogram[grey]++;
            stat[0] = Math.max(histogram[grey], stat[0]);
        });
        double mx = Math.max(0, Math.log10(stat[0]));
        BufferedImage result = zeichneHistogram(histogram, 256, mx);
        BufferedImage output = zeichneBeschriftung(result, mx);
        ImageIO.write(output, "png", out);
    }

    public static BufferedImage zeichneHistogram(int histo[], int height, double mx) {
        double scale = 256.0 / mx;
        BufferedImage img = new BufferedImage(histo.length, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < histo.length; i++) {
            //Verwende den 10er Logarithmus um große Werte darstellen zu können.
            int to = (int) (scale * Math.log10(histo[i]));
            //Begrenze werte auf den Bereich des Bild
            to = Math.max(0, Math.min(height - 1, to));
            //Färbe eine Spalte ein
            for (int j = 0; j < height; j++) {
                img.setRGB(i, j, ((j >= to) ? Color.white : Color.black).getRGB());
            }
        }
        return img;
    }

    public static BufferedImage zeichneBeschriftung(BufferedImage source, double mx) {
        double scale = source.getHeight() / mx;
        BufferedImage result = new BufferedImage(30 + source.getWidth(), 30 + source.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D ctx = result.createGraphics();
        //Zeichne die Achsen und das Histogram
        Graphics2D ctx1 = (Graphics2D) ctx.create();
        ctx1.scale(1, -1);
        ctx1.translate(0, -result.getHeight());
        ctx1.setColor(Color.white);
        ctx1.fillRect(0, 0, result.getWidth(), result.getHeight());
        ctx1.drawImage(source, 30, 30, null);
        ctx1.setColor(Color.lightGray);
        ctx1.drawRect(28, 0, 1, result.getHeight());
        ctx1.drawRect(0, 29, result.getWidth(), 1);
        ctx1.dispose();
        //Zeichne die Beschriftung
        Graphics2D ctx2 = (Graphics2D) ctx.create();
        ctx2.setColor(Color.darkGray);
        ctx2.drawString("0", 35, result.getHeight() - 15);
        ctx2.drawString("0", 20, result.getHeight() - 35);
        for (int i = 1; i <= Math.ceil(mx); i++) {
            String txt = String.format("1e%d", i);
            ctx2.drawString(txt, 5, (int) (result.getHeight() - 35 - i * scale));
        }
        ctx2.drawString("255", result.getWidth() - 25, result.getHeight() - 15);
        ctx2.dispose();
        return result;
    }
}