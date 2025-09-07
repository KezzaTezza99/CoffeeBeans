package Engine.GenericUIComponents;
import Engine.GameWindow;
import Engine.States.STATES;
import Engine.Utility.TextPositionHelper;
import java.awt.*;

// TODO: Can make this very generic and decide where buttons are placed within different constructors?
// Should probably have three buttons, one for Restarting, Returning to Menu, Quitting the game?
public class InteractiveSplashScreen {
    // TODO: Think about how we are handling this GameWindow Injection, could game settings i.e., height width be
    // injected into a settings class where we can access basic information such as dimensions for cases like this?!
    private final GameWindow gameWindow;
    private final String message;

    private final int x;
    private final int y;

    // TODO: Get a global font object
    private final Font font;

    // Drawing a restart and exit button
    private final UIButton restartGameButton;
    private final UIButton exitGameButton;

    // TODO: Can expand with a location flag for deciding where to draw text / buttons
    public InteractiveSplashScreen(GameWindow gameWindow, String message) {
        this.gameWindow = gameWindow;
        this.message = message;
        this.font = new Font("Default", Font.BOLD, 48);

        // Getting the centre point to display the text
        Point centrePoint = TextPositionHelper.getCentredTextPos(message, font, gameWindow.getScreenWidth(), gameWindow.getScreenHeight());
        this.x = centrePoint.x;
        this.y = centrePoint.y;

        // Placing the buttons under the centre text with a predetermined spacing
        int buttonPadding = 50;
        restartGameButton = new UIButton(this.x, (this.y + buttonPadding), "Restart", this.font, 25, this::resetGame);
        exitGameButton = new UIButton(this.x, (restartGameButton.getY() + restartGameButton.getHeight() + buttonPadding), restartGameButton.getWidth(), restartGameButton.getHeight(), "Exit", this::exitTheGame);
    }

    public void draw(Graphics2D graphics2D) {
        // Drawing a semi-transparent overlay
        graphics2D.setColor(new Color(0, 0,0,150));
        graphics2D.fillRect(0, 0, gameWindow.getWidth(), gameWindow.getHeight());

        // Drawing centred text
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(this.font);
        graphics2D.drawString(message, x, y);

        // Drawing the buttons
        restartGameButton.draw(graphics2D, Color.blue, Color.white);
        exitGameButton.draw(graphics2D, Color.blue, Color.white);
    }

    // The user want's to restart the game so we need to reinitialise everything!!!
    private void resetGame() {
        // TODO: actually reset the game
        this.gameWindow.init();
    }

    // The user want's to exit the application so let's exit the game!
    private void exitTheGame() { System.exit(0); }

    // Getters
    public UIButton getRestartGameButton() {
        return restartGameButton;
    }
    public UIButton getExitGameButton() {
        return exitGameButton;
    }
}