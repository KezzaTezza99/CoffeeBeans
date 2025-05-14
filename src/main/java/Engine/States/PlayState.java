package Engine.States;
import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.GameStateManager;

import java.awt.*;

public class PlayState extends GameState {
    GameStateManager gameStateManager;

    public PlayState(GameStateManager gm, boolean isActive) {
        super(gm, isActive);
        gameStateManager = gm;
    }

    @Override
    public void update() {
        if(!isBlockUpdate()) {
            gameStateManager.getGameContext().getEntityManager().update();
            gameStateManager.getGameContext().getSoundManager().loop("test");
        }
    }

    @Override
    public void input(KeyHandler keyHandler) {
        if (keyHandler.escape.down) {
            gameStateManager.setGameStateIsActive(STATES.PLAY, false);
            gameStateManager.setGameStateIsActive(STATES.MAIN_MENU, true);
            gameStateManager.setCurrentState(STATES.MAIN_MENU);
        }

        if (keyHandler.pause.clicked && gameStateManager.getCurrentState() == STATES.PLAY && !gameStateManager.pauseFlag) {
            gameStateManager.queueStateSwitchPauseAndPlay(STATES.PAUSE, true);
        }
    }

    @Override
    public void input(KeyHandler keyHandler, MouseHandler mouseHandler) {
        if(mouseHandler.isClicked()) {
            gameStateManager.getGameContext().getEntityManager().handleMouseClickOnAABBBounds();
            mouseHandler.resetClick();
        }

        input(keyHandler);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        gameStateManager.getGameContext().getTileManager().draw(graphics2D);
        gameStateManager.getGameContext().getEntityManager().draw(graphics2D);
        gameStateManager.getGameContext().getUiManager().draw(graphics2D);
    }
}