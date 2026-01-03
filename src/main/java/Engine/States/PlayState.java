package Engine.States;
import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.GameStateManager;
import Engine.Services.GameContextService;

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
            GameContextService.get().getEntityManager().update();
            GameContextService.get().getEntityManager().removeDeadEntities();
            GameContextService.get().getUiManager().update();
            GameContextService.get().getSoundManager().loop("test");
        }
    }

    @Override
    public void input(KeyHandler keyHandler) {
        if (keyHandler.pause.clicked && gameStateManager.getCurrentState() == STATES.PLAY && !gameStateManager.pauseFlag) {
            gameStateManager.queueStateSwitchPauseAndPlay(STATES.PAUSE, true);
        }
    }

    @Override
    public void input(KeyHandler keyHandler, MouseHandler mouseHandler) {
        if(mouseHandler.isClicked()) {
            GameContextService.get().getEntityManager().handleMouseClickOnAABBBounds();
            mouseHandler.resetClick();
        }

        input(keyHandler);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        GameContextService.get().getTileManager().draw(graphics2D);
        GameContextService.get().getEntityManager().draw(graphics2D);
        GameContextService.get().getUiManager().draw(graphics2D);
    }
}