package Engine.States;
import Engine.GenericUIComponents.PauseSplashScreen;
import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.GameStateManager;
import java.awt.*;

public class PauseState extends GameState {
    private final GameStateManager gameStateManager;
    private final PauseSplashScreen pauseSplashScreen;

    public PauseState(GameStateManager gm, boolean isActive) {
        super(gm, isActive);
        this.gameStateManager = gm;
        this.pauseSplashScreen = new PauseSplashScreen(gm.getGameWindow());
    }

    @Override
    public void update() {

    }

    @Override
    public void input(KeyHandler keyHandler) {
        if (keyHandler.pause.clicked && gameStateManager.getCurrentState() == STATES.PAUSE && gameStateManager.pauseFlag) {
            gameStateManager.queueStateSwitchPauseAndPlay(STATES.PLAY, false);
        }
    }

    @Override
    public void input(KeyHandler keyHandler, MouseHandler mouseHandler) {
        if(mouseHandler.isClicked()) {
            pauseSplashScreen.getResumeButton().handleClick(mouseHandler.getMouseX(), mouseHandler.getMouseY());
            pauseSplashScreen.getOptionsButton().handleClick(mouseHandler.getMouseX(), mouseHandler.getMouseY());
            pauseSplashScreen.getExitToMainMenuButton().handleClick(mouseHandler.getMouseX(), mouseHandler.getMouseY());
            pauseSplashScreen.getExitToDesktopButton().handleClick(mouseHandler.getMouseX(), mouseHandler.getMouseY());
            mouseHandler.resetClick();
        }
        input(keyHandler);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        pauseSplashScreen.draw(graphics2D);
    }
}