package Engine;
import Engine.Entity.NPC;
import Engine.Entity.Player;
import Engine.Entity.Enemy;
import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.*;
import Engine.Managers.UIManager;
import Engine.Services.GameContextService;
import Engine.States.STATES;
import javax.swing.*;
import java.awt.*;

/**
 * The game window will be responsible for creating a panel for the tile manager to draw its sprites too
 * Game window will store useful information such as:
 * Sprite size
 * Scale
 * Aspect Ratio
 * Screen width and height
 * If the developer doesn't provide this information it will be defaulted here.
 * */
public class GameWindow extends JPanel implements Runnable {
    String title = "CoffeeBeans";

    // Default values that can be overridden by the developer (64x64)
    int tileSize;

    // Creating the maximum amount of screen columns / rows we can display sprites to get an aspect ratio of 4:3
    // The max sprites will be (30 x 16)
    int maxScreenCol;
    int maxScreenRow;

    // The max screen width and height (1920 x 1080)
    int screenWidth;
    int screenHeight;

    Thread mainThread;
    private boolean isRunning = false;
    double FPS = 60;

    private GameStateManager gameStateManager;

    // TODO: COULD WE HANDLE THIS IN GAME STATE MANAGER ? OR SHOULD WE HAVE A GAME CLASS?
    public Player player;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    public Enemy enemy;
    public Enemy enemy2;
    public Enemy enemy3;
    public NPC npc;
    public EntityManager entityManager;

    // Testing sound
    public SoundManager soundManager;

    // Testing UI
    public UIManager uiManager;

    // Testing passing around one main object
    public CollisionManager collisionManager;
    public TileManager tileManager;

    public boolean replayGame = false;

    public GameWindow() {
        // Setting up the game window
        this.setPreferredSize(new Dimension(1920,1080));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        tileSize = 64;
        maxScreenCol = 30;
        maxScreenRow = 17;
        screenWidth = 1920;
        screenHeight = 1080;

        init(STATES.MAIN_MENU);
    }

    public GameWindow(int screenWidth, int screenHeight, int tileSize, int maxScreenX, int maxScreenY) {
        // Setting up the game window
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        this.tileSize = tileSize;
        this.maxScreenCol = maxScreenX;
        this.maxScreenRow = maxScreenY;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        init(STATES.MAIN_MENU);
    }

    public void init(STATES stateToInit) {
        isRunning = true;
        keyHandler = new KeyHandler(this);
        mouseHandler = new MouseHandler(this);

        // Construct stuff with min dependencies
        tileManager = new TileManager(this, false);
        collisionManager = new CollisionManager();
        uiManager = new UIManager();
        soundManager = new SoundManager();
        entityManager = new EntityManager(mouseHandler);

        GameContextService.initGameContext(
                tileManager,
                collisionManager,
                uiManager,
                soundManager,
                entityManager,
                keyHandler,
                mouseHandler,
                this);

        createEntities();

        // Now construct game states because entities exist
        gameStateManager = new GameStateManager(this, GameContextService.get(), stateToInit);
    }

    // TODO: This is called when we are on main-menu and not actually clicked play
    public void resetWorld() {
        cleanup();

        uiManager.resetHealthBar();
        gameStateManager.pauseFlag = false;

        createEntities();
    }

    // Override that is called when the JPanel is created
    public void addNotify() {
        super.addNotify();

        // If we haven't started this thread, start one
        if(mainThread == null) {
            mainThread = new Thread(this, "GameThread");
            mainThread.start();
        }
    }

    @Override
    public void run() {
        // Time step for limiting FPS
        double drawInterval = 1000000000.0 / FPS; // 1 second / FPS
        double delta = 0;
        long lastTime = System.nanoTime();

        while (isRunning) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                // Update game state and redraw
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        keyHandler.tick();
        gameStateManager.input(keyHandler, mouseHandler);
        gameStateManager.update();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        gameStateManager.draw(graphics2D);
        graphics2D.dispose();
    }

    private void createEntities() {
        // Construct the entities now game context is ready
        player = new Player();
        enemy = new Enemy();
        enemy2 = new Enemy(128 * 4, 128);
        enemy3 = new Enemy(128 * 3, 128);
        npc = new NPC();

        // Register the entities
        entityManager.addEntity(player);
        entityManager.addEntity(enemy);
        entityManager.addEntity(enemy2);
        entityManager.addEntity(enemy3);
        entityManager.addEntity(npc);
    }

    private void cleanup() {
        soundManager.closeAll(false);
        entityManager.removeAllEntities();
    }

    // Some useful getters
    public int getTileSize() {
        return tileSize;
    }
    public int getMaxScreenCol() {
        return maxScreenCol;
    }
    public int getMaxScreenRow() {
        return maxScreenRow;
    }
    public int getScreenWidth() {
        return screenWidth;
    }
    public int getScreenHeight() {
        return screenHeight;
    }
    public int getHalfScreenWidth() { return screenWidth / 2; }
    public int getHalfScreenHeight() { return screenHeight / 2; }
    public GameStateManager getGameStateManager() { return gameStateManager; }
}