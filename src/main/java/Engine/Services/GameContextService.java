package Engine.Services;

import Engine.GameContext;
import Engine.GameWindow;
import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.*;

public final class GameContextService {
    private static GameContext gameContext;
    private GameContextService() {}

    public static void initGameContext(GameWindow gw,
                                       TileManager tm,
                                       CollisionManager cm,
                                       UIManager uim,
                                       SoundManager sm,
                                       EntityManager em,
                                       KeyHandler kh,
                                       MouseHandler mh,
                                       GameStateManager gsm
    ) {
        if(gameContext != null) {
            throw new IllegalStateException("GameContext already initialized");
        }
        gameContext = new GameContext(gw,tm, cm, em, uim, sm, kh, mh, gsm);
    }

    public static GameContext get() {
        if(gameContext == null) {
            throw new IllegalStateException("GameContext not initialized yet!");
        }
        return gameContext;
    }
}