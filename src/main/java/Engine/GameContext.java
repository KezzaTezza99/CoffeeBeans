package Engine;

import Engine.Managers.CollisionManager;
import Engine.Managers.TileManager;

// TODO: Eventually will have a bunch of important dependencies for us, then we inject the GX into classes we need!
public class GameContext {
    private final TileManager tileManager;
    private final CollisionManager collisionManager;

    public GameContext(TileManager tm, CollisionManager cm) {
        this.tileManager = tm;
        this.collisionManager = cm;
    }

    public TileManager getTileManager() {
        return tileManager;
    }
    public CollisionManager getCollisionManager() {
        return collisionManager;
    }
}