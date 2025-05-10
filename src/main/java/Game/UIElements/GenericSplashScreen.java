package Game.UIElements;
import Engine.GameWindow;

import java.awt.*;

public class GenericSplashScreen {
    // For now injecting the GameWindow
    // TODO: How do I want to handle dependencies?
    private GameWindow gameWindow;

    private String message;

    public GenericSplashScreen(GameWindow gameWindow, String message) {
        this.gameWindow = gameWindow;
        this.message = message;
    }

    public void draw(Graphics2D graphics2D) {
        // Drawing a semi-transparent overlay
        graphics2D.setColor(new Color(0, 0,0,150));
        graphics2D.fillRect(0, 0, gameWindow.getWidth(), gameWindow.getHeight());

        // Drawing centred text
        // TODO: Should I have predetermined text locations and pass this in?
        // TODO: It would be more performative todo this on initialisation so should adjust

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 48f));

        // Getting font size
        FontMetrics metrics = graphics2D.getFontMetrics();

        int x = (gameWindow.getScreenWidth() - metrics.stringWidth(message)) / 2;
        int y = (gameWindow.getScreenHeight() / 2);

        graphics2D.drawString(message, x, y);
    }
}