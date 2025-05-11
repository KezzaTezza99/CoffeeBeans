package Engine;

import Engine.Managers.*;

// TODO: Eventually will have a bunch of important dependencies for us, then we inject the GX into classes we need!
public class GameContext {
    private final TileManager tileManager;
    private final CollisionManager collisionManager;
    private final EntityManager entityManager;
    private final UIManager uiManager;
    private final SoundManager soundManager;

    public GameContext(TileManager tm, CollisionManager cm, EntityManager em, UIManager ui, SoundManager sm) {
        this.tileManager = tm;
        this.collisionManager = cm;
        this.entityManager = em;
        this.uiManager = ui;
        this.soundManager = sm;
    }

    public TileManager getTileManager() {
        return tileManager;
    }
    public CollisionManager getCollisionManager() {
        return collisionManager;
    }
    public EntityManager getEntityManager() {
        return entityManager;
    }
    public UIManager getUiManager() {
        return uiManager;
    }
    public SoundManager getSoundManager() {
        return soundManager;
    }
}