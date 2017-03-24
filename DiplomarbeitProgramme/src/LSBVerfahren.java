
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Simon Lehner-Dittenberger.
 */
public class LSBVerfahren {

    public static final int SEED = 1234;
    
    // if true the input is repeated if there is still space in the file
    public static boolean repeat = true;

    public static int bitpair(byte data[], int offset) {
        if (data.length == 0 || !repeat && offset >= data.length * 8) {
            return 0;
        } else {
            offset = offset % (data.length * 8);
        }

        return (data[offset / 8] >> (offset % 8)) & 0x3;
    }

    public static void encode(BufferedImage image, byte[] data) {
        Random random = new Random(SEED);
        int bitOffset = -2;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getRGB(x, y);
                pixel = (pixel & (~0x030303)); //overwrite the two LSB bits with 0
                pixel |= (bitpair(data, bitOffset += 2) ^ random.nextInt(4)) << 16;
                pixel |= (bitpair(data, bitOffset += 2) ^ random.nextInt(4)) << 8;
                pixel |= (bitpair(data, bitOffset += 2) ^ random.nextInt(4)) << 0;
                image.setRGB(x, y, pixel);
            }
        }
    }

    private static byte[] decode(BufferedImage image) {
        Random random = new Random(SEED);
        byte[] result = new byte[1 + (image.getWidth() * image.getHeight() * 6) / 8];
        int bitOffset = -2;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getRGB(x, y);
                bitOffset += 2;
                result[bitOffset / 8] |= (((pixel >> 16) ^ random.nextInt(4)) & 0x3) << ((bitOffset) % 8);
                bitOffset += 2;
                result[bitOffset / 8] |= (((pixel >> 8) ^ random.nextInt(4)) & 0x3) << ((bitOffset) % 8);
                bitOffset += 2;
                result[bitOffset / 8] |= (((pixel >> 0) ^ random.nextInt(4)) & 0x3) << ((bitOffset) % 8);
            }
        }
        return result;
    }

    public static void extractDataFromImage(String args[]) throws IOException {
        BufferedImage image = ImageIO.read(new File("lsb-hidden-data.png"));//load the original image
        byte data[] = decode(image);//extract payload from image
        Files.write(new File("lsb-output.txt").toPath(), data, WRITE, CREATE);//write extracted payload to hdd to test if it worked
    }

    public static void encodeDataIntoImage(String args[]) throws IOException {
        BufferedImage image = ImageIO.read(new File("earth.png"));//load the original image
        byte data[] = Files.readAllBytes(new File("lsb-input.txt").toPath());//load the original payload file
        encode(image, data);//add payload to image
        ImageIO.write(image, "png", new File("lsb-hidden-data.png"));//write image with payload to hdd
    }

    public static void main(String[] args) throws IOException {
//        System.out.println("encoding");
//        encodeDataIntoImage(args);
        System.out.println("extracting");
        extractDataFromImage(args);
    }
}
