package Engine.States;
import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.GameStateManager;
import java.awt.*;

public class PauseState extends GameState {
    private GameStateManager gameStateManager;

    public PauseState(GameStateManager gm, boolean isActive) {
        super(gm, isActive);
        this.gameStateManager = gm;
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
        input(keyHandler);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.BLUE);
        graphics2D.fillRoundRect(
                100,
                gameStateManager.getGameWindow().getScreenHeight() / 4,
                (gameStateManager.getGameWindow().getScreenWidth() - 210),
                600, 35 , 35);

        graphics2D.setColor(Color.white);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24F));
        graphics2D.drawString("The game is now paused", gameStateManager.getGameWindow().getScreenWidth() / 2, gameStateManager.getGameWindow().getScreenHeight() / 3);
    }
}