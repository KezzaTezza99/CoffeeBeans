package Engine.States;
import Engine.Input.KeyHandler;
import Engine.Managers.GameStateManager;

import java.awt.*;

public class PlayState extends GameState {
    private GameStateManager gameStateManager;

    public PlayState(GameStateManager gm, boolean isActive) {
        super(gm, isActive);
        this.gameStateManager = gm;
    }

    @Override
    public void update() {

    }

    @Override
    public void input(KeyHandler keyHandler) {
        keyHandler.escape.tick();

        if (keyHandler.escape.down && gameStateManager.getCurrentState() == STATES.PLAY) {
            gameStateManager.setGameStateIsActive(STATES.PLAY,false);
            gameStateManager.setGameStateIsActive(STATES.PAUSE, true);
            gameStateManager.setCurrentState(STATES.PAUSE);

            System.out.println("Pausing the game");
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.blue);
        graphics2D.drawRect(500, 100, 200, 200);
    }
}