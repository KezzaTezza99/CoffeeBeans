package engine_main.utilities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tools {

    //Scales an image to the desired tile size
    public BufferedImage scaleAImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(original, 0, 0, width, height, null);
        graphics2D.dispose();

        return scaledImage;
    }
}
