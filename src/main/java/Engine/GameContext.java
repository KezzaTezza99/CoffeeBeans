package Engine;

import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.*;

// TODO: Eventually will have a bunch of important dependencies for us, then we inject the GX into classes we need!
//TODO: Now GameContext has all we need may as well get rid of dependencies in classes and just use the service?
public class GameContext {
    private final GameWindow gameWindow;
    private final TileManager tileManager;
    private final CollisionManager collisionManager;
    private final EntityManager entityManager;
    private final UIManager uiManager;
    private final SoundManager soundManager;
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private final GameStateManager gameStateManager;

    public GameContext(GameWindow gw,
                       TileManager tm,
                       CollisionManager cm,
                       EntityManager em,
                       UIManager ui,
                       SoundManager sm,
                       KeyHandler kh,
                       MouseHandler mh,
                       GameStateManager gsm) {
        this.gameWindow = gw;
        this.tileManager = tm;
        this.collisionManager = cm;
        this.entityManager = em;
        this.uiManager = ui;
        this.soundManager = sm;
        this.keyHandler = kh;
        this.mouseHandler = mh;
        this.gameStateManager = gsm;
    }

    public GameWindow getGameWindow() { return gameWindow; }
    public TileManager getTileManager() {
        return tileManager;
    }
    public CollisionManager getCollisionManager() { return collisionManager; }
    public EntityManager getEntityManager() {
        return entityManager;
    }
    public UIManager getUiManager() {
        return uiManager;
    }
    public SoundManager getSoundManager() {
        return soundManager;
    }
    public KeyHandler getKeyHandler() { return keyHandler; }
    public MouseHandler getMouseHandler() { return mouseHandler; }
    public GameStateManager getGameStateManager() { return gameStateManager; }
}