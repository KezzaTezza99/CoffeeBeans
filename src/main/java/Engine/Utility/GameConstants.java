package Engine.Utility;

public final class GameConstants {
    // --- GAME WINDOW INFO ---
    public static final int SCREEN_WIDTH = 1920;
    public static final int SCREEN_HEIGHT = 1080;
    public static final int FPS = 60;
    public static final int HALF_SCREEN_WIDTH = SCREEN_WIDTH / 2;
    public static final int HALF_SCREEN_HEIGHT = SCREEN_HEIGHT / 2;

    // --- GAME SPRITE / TILE INFO ---
    public static final int TILE_SIZE = 64;
    public static final int MAX_SCREEN_COL = 30;
    public static final int MAX_SCREEN_ROW = 17;

    // --- DAMAGE STATS ---
    public static final int PLAYER_DAMAGE_TO_ENEMY = 20;
    public static final int ENEMY_DAMAGE_TO_PLAYER = 25;

    // --- COOLDOWN STATS ---
    public static final int ENEMY_ATTACK_DELAY = 30;                // A 0.5-second delay
}
