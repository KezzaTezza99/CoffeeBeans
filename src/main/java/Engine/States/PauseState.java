package Engine.States;
import Engine.Input.KeyHandler;
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
        keyHandler.up.tick();

        if (keyHandler.up.clicked && gameStateManager.getCurrentState() == STATES.PAUSE) {
            gameStateManager.setGameStateIsActive(STATES.PAUSE,false);
            gameStateManager.setGameStateIsActive(STATES.PLAY, true);
            gameStateManager.setCurrentState(STATES.PLAY);

            System.out.println("Unpausing the game");
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.red);
        graphics2D.drawRect(100, 100, 200, 200);
    }
}