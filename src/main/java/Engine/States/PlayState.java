package Engine.States;
import Engine.Input.KeyHandler;
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
            System.exit(0);
        }

//        if(keyHandler.pause.clicked && gameStateManager.getCurrentState() == STATES.PLAY) {
////            pauseFlag = !pauseFlag;
////            // we should really change GAME STATE
//////            this.setActive(false);
////            tileManager.setDrawPauseMenu(pauseFlag);
//            if(!gameStateManager.pauseFlag) {
//                System.out.println("WE HAVE PAUSED THE GAME");
//                gameStateManager.setCurrentState(STATES.PAUSE);
//                gameStateManager.setGameStateIsActive(STATES.PLAY, false);
//                gameStateManager.setGameStateIsActive(STATES.PAUSE, true);
//                gameStateManager.setPauseFlag(true);
//            }
//        }
//    }

//        if(keyHandler.pause.clicked && gameStateManager.getCurrentState() == STATES.PLAY && !gameStateManager.pauseFlag) {
//            System.out.println("WE HAVE PAUSED THE GAME");
//            gameStateManager.setGameStateIsActive(STATES.PAUSE, true);
//            gameStateManager.setGameStateIsActive(STATES.PLAY, false);
//            gameStateManager.setCurrentState(STATES.PAUSE);
//            gameStateManager.setPauseFlag(true);
//        }

        if (keyHandler.pause.clicked && gameStateManager.getCurrentState() == STATES.PLAY && !gameStateManager.pauseFlag) {
            System.out.println("WE HAVE PAUSED THE GAME");
            gameStateManager.queueStateSwitchPauseAndPlay(STATES.PAUSE, true);
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        tileManager.draw(graphics2D);
    }

    public TileManager getTileManager() { return tileManager; }
}