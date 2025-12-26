package Engine.Services;

import Engine.GameContext;
import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.*;

public final class GameContextService {
    private static GameContext gameContext;
    private GameContextService() {}

    public static void initGameContext(TileManager tm,
                                       CollisionManager cm,
                                       UIManager uim,
                                       SoundManager sm,
                                       EntityManager em,
                                       KeyHandler kh,
                                       MouseHandler mh) {
        if(gameContext != null) {
            throw new IllegalStateException("GameContext already initialized");
        }
        gameContext = new GameContext(tm, cm, em, uim, sm, kh, mh);
    }

    public static GameContext get() {
        if(gameContext == null) {
            throw new IllegalStateException("GameContext not initialized yet!");
        }
        return gameContext;
    }
}