/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whitening;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author Simon
 */
public class Main {

    public static class Point {

        public final double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

    }

    public static double square(double a) {
        return a * a;
    }

    public static double mittlereQuadratischeKontingenz(List<Point> points) {
        double sum = 0;
        double nx1 = points.stream().mapToDouble(Point::getX).sum();
        double nx2 = points.stream().mapToDouble(Point::getY).sum();
        double n = nx1 + nx2;
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            {
                double down = (nx1 * (p.x + p.y)) / n;
                double up = p.x - down;
                sum += up * up / down;
            }
            {
                double down = (nx2 * (p.x + p.y)) / n;
                double up = p.y - down;
                sum += up * up / down;
            }
        }
        return sum;
    }

    public static double mqk(List<Point> points) {
        double v =  mittlereQuadratischeKontingenz(points);
        return Math.sqrt(v / (v + points.size() * 2));
    }

    public static double cov(Collection<Point> points) {
        double xStrich = points.stream().mapToDouble(Point::getX).average().orElse(Double.NaN);
        double yStrich = points.stream().mapToDouble(Point::getY).average().orElse(Double.NaN);
        double covxy = points.stream().mapToDouble(p -> (p.x - xStrich) * (p.y - yStrich)).sum();
        double covx = points.stream().mapToDouble(p -> (p.getX() - xStrich)).map(Main::square).sum();
        double covy = points.stream().mapToDouble(p -> (p.getY() - yStrich)).map(Main::square).sum();
        return covxy / Math.sqrt(covx * covy);
//        return covxy / (points.size());
//        return covxy / covx;
    }

    public static double test(List<Point> points) {
        double xy = points.stream().mapToDouble(p -> p.x * p.y).sum();
        double x = points.stream().mapToDouble(p -> p.x).sum();
        double y = points.stream().mapToDouble(p -> p.y).sum();
        double xx = points.stream().mapToDouble(p -> p.x * p.x).sum();
        double beta2 = (xy - x * y) / (xx - x * x);
        double beta1 = ((xx * y) - (x * xy)) / (xx - x * x);
        double sum = 0;
        for(Point p : points) {;
            sum += square( p.y - (beta1 + beta2 * p.x));
        }
        return sum;
    }

    public static double doit(byte data[], Function<List<Point>, Double> cons) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            points.add(new Point(i + 1, (int) data[i] + 128));
        }
        return cons.apply(points);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        Random random = new Random(0);
        byte[] data = Data.loremParagraph1.getBytes("US-ASCII");
        byte[] rand = new byte[data.length];
        double dataSum = 0, randSum = 0;
        for (int i = 0; i < data.length; i++) {
            rand[i] = (byte) random.nextInt(256);
//            data[i] = (byte) (i/6);
//            data[i] = 12;
            System.out.printf("%d\t%d\t%d\n", i + 1, data[i], rand[i] + 128);
        }
        double norm = (256*256*460*0.5);
        System.out.printf("cov %f %f\n", doit(data, Main::mqk), doit(rand, Main::mqk));
    }
}
