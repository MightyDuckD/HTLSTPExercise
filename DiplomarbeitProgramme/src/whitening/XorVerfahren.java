package whitening;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import static whitening.Main.doit;

/**
 *
 * @author Simon Lehner-Dittenberger <simon.lehnerd@gmail.com>
 */
public class XorVerfahren {

    public static void main(String[] args) throws UnsupportedEncodingException {
        Random random = new Random(0);
        byte[] data = Data.loremParagraph1.getBytes("US-ASCII");
        byte[] rand = new byte[data.length];
        double dataSum = 0, randSum = 0;
        for (int i = 0; i < data.length; i++) {
            rand[i] = (byte) random.nextInt(256);
//            data[i] = (byte) (data[i] ^ rand[i]);
            System.out.printf("%d\t%d\t%d\t%d\n", i + 1, data[i] & 0xFF, rand[i] & 0xFF, (data[i] ^ rand[i]) & 0xFF);
        }
    }
}
