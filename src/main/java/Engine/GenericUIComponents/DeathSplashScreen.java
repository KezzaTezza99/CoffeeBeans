package Engine.GenericUIComponents;
import Engine.GameWindow;
import Engine.Services.GameContextService;
import Engine.States.STATES;
import Engine.Utility.GameConstants;
import Engine.Utility.TextPositionHelper;
import java.awt.*;

// TODO: Why can't we just do this in the state instead?

// TODO: Can make this very generic and decide where buttons are placed within different constructors?
// Should probably have three buttons, one for Restarting, Returning to Menu, Quitting the game?
public class DeathSplashScreen {
    private final String message;

    private final int x;
    private final int y;

    // TODO: Get a global font object
    private final Font font;

    // Drawing a restart and exit button
    private final UIButton restartGameButton;
    private final UIButton exitGameButton;

    // TODO: Can expand with a location flag for deciding where to draw text / buttons
    public DeathSplashScreen(String message) {
        this.message = message;
        this.font = new Font("Default", Font.BOLD, 48);

        // Getting the centre point to display the text
        Point centrePoint = TextPositionHelper.getCentredTextPos(message, font, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
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
        graphics2D.fillRect(0, 0, GameContextService.get().getGameWindow().getWidth(), GameContextService.get().getGameWindow().getHeight());

        // Drawing centred text
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(this.font);
        graphics2D.drawString(message, x, y);

        // Drawing the buttons
        restartGameButton.draw(graphics2D, Color.blue, Color.white);
        exitGameButton.draw(graphics2D, Color.blue, Color.white);
    }

    private void resetGame() {
        GameContextService.get().getGameStateManager().setGameStateIsActive(STATES.GAME_OVER, false);
        GameContextService.get().getGameStateManager().setGameStateIsActive(STATES.PLAY, true);
        GameContextService.get().getGameStateManager().setCurrentState(STATES.PLAY);
        GameContextService.get().getGameWindow().resetWorld();
    }

    // The user want's to exit the application so let's exit the game!gam
    private void exitTheGame() { System.exit(0); }

    // Getters
    public UIButton getRestartGameButton() {
        return restartGameButton;
    }
    public UIButton getExitGameButton() {
        return exitGameButton;
    }
}