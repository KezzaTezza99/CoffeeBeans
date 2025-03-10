package Engine.States;
import Engine.Input.KeyHandler;
import Engine.Managers.GameStateManager;
import Engine.Managers.TileManager;

import java.awt.*;

public class PlayState extends GameState {
    TileManager tileManager;

    public PlayState(GameStateManager gm, boolean isActive) {
        super(gm, isActive);

        // Initialise the TileManager and handle input
        tileManager = new TileManager(gm.getGameWindow(), false);
        //KeyHandler input = new KeyHandler(gameStateManager.getGameWindow());
    }

    @Override
    public void update() {

    }

    @Override
    public void input(KeyHandler keyHandler) {
        if(keyHandler.escape.down) {
            System.exit(0);
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        tileManager.draw(graphics2D);
    }

    public TileManager getTileManager() { return tileManager; }
}