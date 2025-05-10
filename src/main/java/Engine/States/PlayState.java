package Engine.States;
import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.GameStateManager;
import Engine.Managers.TileManager;

import java.awt.*;

// TODO: TRYING TO GAVE GAME WINDOW IN THE PLAY STATE SO WE CAN ACCESS THE GAMESTATE MANAGER
// OR COULD ALL STATES GET THE GAMESTATE MANAGER?
public class PlayState extends GameState {
    TileManager tileManager;
    //boolean pauseFlag = false;
    GameStateManager gameStateManager;

    public PlayState(GameStateManager gm, boolean isActive) {
        super(gm, isActive);

        gameStateManager = gm;

        // Initialise the TileManager and handle input
        tileManager = new TileManager(gm.getGameWindow(), false);
        //KeyHandler input = new KeyHandler(gameStateManager.getGameWindow());
    }

    @Override
    public void update() {
    }

    // TODO: All temp logic here
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
        input(keyHandler);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        tileManager.draw(graphics2D);
    }

    public TileManager getTileManager() { return tileManager; }
}