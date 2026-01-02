package Engine.States;
import Engine.GenericUIComponents.DeathSplashScreen;
import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.GameStateManager;
import java.awt.*;

public class GameOverState extends GameState {
    private final GameStateManager gameStateManager;
    private DeathSplashScreen gameOverSplashScreen;

    public GameOverState(GameStateManager gm, boolean isActive) {
        super(gm, isActive);
        this.gameStateManager = gm;
        setBlockUpdate(false);
        gameOverSplashScreen = new DeathSplashScreen("YOU DIED");
    }

    @Override
    public void update() {}
    @Override
    public void input(KeyHandler keyHandler) {}

    @Override
    public void input(KeyHandler keyHandler, MouseHandler mouseHandler) {
        if(mouseHandler.isClicked()) {
            gameOverSplashScreen.getRestartGameButton().handleClick(mouseHandler.getMouseX(), mouseHandler.getMouseY());
            gameOverSplashScreen.getExitGameButton().handleClick(mouseHandler.getMouseX(), mouseHandler.getMouseY());
            mouseHandler.resetClick();
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        gameOverSplashScreen.draw(graphics2D);
    }
}