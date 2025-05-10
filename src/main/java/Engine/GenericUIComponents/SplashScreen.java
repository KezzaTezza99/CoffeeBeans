package Engine.GenericUIComponents;
import Engine.GameWindow;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SplashScreen {
    // For now injecting the GameWindow
    // TODO: How do I want to handle dependencies?
    private final GameWindow gameWindow;
    private final String message;

    private int x, y;
    private final Font font;

    public SplashScreen(GameWindow gameWindow, String message) {
        this.gameWindow = gameWindow;
        this.message = message;
        this.font = new Font("Default", Font.BOLD, 48);

        // TODO: Should I have predetermined text locations and pass this in?
        computeDefaultTextPos();
    }

    private void computeDefaultTextPos() {
        BufferedImage temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = temp.createGraphics();

        graphics2D.setFont(this.font);

        FontMetrics metrics = graphics2D.getFontMetrics();

        this.x = (gameWindow.getScreenWidth() - metrics.stringWidth(message)) / 2;
        this.y = (gameWindow.getScreenHeight() / 2);

        graphics2D.dispose();
    }

    public void draw(Graphics2D graphics2D) {
        // Drawing a semi-transparent overlay
        graphics2D.setColor(new Color(0, 0,0,150));
        graphics2D.fillRect(0, 0, gameWindow.getWidth(), gameWindow.getHeight());

        // Drawing centred text
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(this.font);
        graphics2D.drawString(message, x, y);
    }
}