package Engine.Utility;
import java.awt.*;
import java.awt.image.BufferedImage;

// A helper class for easily placing text in certain common positions, for now just centre but eventually will expand
// for top left etc. (mini-maps, health etc.)
// TODO: Could just become a UI helper class and be a lot more general?
public class TextPositionHelper {
    public static Point getCentredTextPos(String text, Font font, int gameWinWidth, int gameWinHeight) {
        BufferedImage temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = temp.createGraphics();

        graphics2D.setFont(font);
        FontMetrics fontMetrics = graphics2D.getFontMetrics();

        int x = (gameWinWidth - fontMetrics.stringWidth(text)) / 2;
        int y = (gameWinHeight / 2);

        graphics2D.dispose();

        return new Point(x, y);
    }

    public static Point getCentredTextPositionInsideUIElement(String text, Font font, int rectX, int rectY, int rectWidth, int rectHeight) {
        BufferedImage temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = temp.createGraphics();

        graphics2D.setFont(font);
        FontMetrics fontMetrics = graphics2D.getFontMetrics();

        // Horizontal Centering
        int x = rectX + (rectWidth - fontMetrics.stringWidth(text)) / 2;
        int y = rectY + (rectHeight - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();

        graphics2D.dispose();

        return new Point(x, y);
    }

    // TODO: finish at a later date? It works as intended I suppose but could be done better maybe?
    public static Point getLeftAlignedTextPositionInsideUIElement(Font font, int rectX, int rectY, int padding) {
        // DRY! - could have a method that returns all this info
        BufferedImage temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = temp.createGraphics();

        graphics2D.setFont(font);
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        //-- end method

        int x = rectX + padding;
        int y = rectY + (fontMetrics.getHeight());

        graphics2D.dispose();

        return new Point(x,y);
    }
}