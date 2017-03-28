
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Simon
 */
public class StereogramGenerator {

    public static void main(String[] args) throws Exception {
	double scale = 25;
        BufferedImage source = ImageIO.read(new File("texture2.jpg"));
        BufferedImage depthmap = ImageIO.read(new File("depthmap.png"));
        BufferedImage result = new BufferedImage(depthmap.getWidth(), depthmap.getHeight(), BufferedImage.TYPE_INT_RGB);
        //Für jede Zeile
        for (int y = 0; y < result.getHeight(); y++) {
            //Kopiere das Muster das erste mal auf der linken Seite
            for(int x = 0; x < source.getWidth(); x++) {
                result.setRGB(x, y, source.getRGB(x, y % source.getHeight()));
            }
            //Kopiere alle weiteren Teile von links verschoben um das Offset aus der Depth Map
            for (int x = source.getWidth(); x < result.getWidth(); x++) {
                result.setRGB(x, y, result.getRGB( x - source.getWidth() + (int)(offset(depthmap, x, y) * scale), y ));
            }
        }
        ImageIO.write(result, "png", new File("what.png"));
    }

    private static double offset(BufferedImage depthmap, int x, int y) {
        return (depthmap.getRGB(x, y) & 0xFF) / 255.0;
    }

}
