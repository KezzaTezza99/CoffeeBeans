package Engine;
import Engine.Entity.Player;
import Engine.Entity.Enemy;
import Engine.Input.KeyHandler;
import Engine.Managers.EntityManager;
import Engine.Managers.GameStateManager;
import Engine.Managers.SoundManager;
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
    private Player player;
    private KeyHandler keyHandler;
    public Enemy enemy;
    public EntityManager entityManager;

    // Testing sound
    public SoundManager soundManager;

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

        init();
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

        init();
    }

    public void init() {
        isRunning = true;
        gameStateManager = new GameStateManager(this);

        keyHandler = new KeyHandler(this);
        player = new Player(this, keyHandler, gameStateManager);
        enemy = new Enemy(this);
        entityManager = new EntityManager();
        entityManager.addEntity(player);
        entityManager.addEntity(enemy);

        soundManager = new SoundManager();
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
        gameStateManager.input(keyHandler);
        gameStateManager.update();

        // Would place all game logic updates in here, i.e. enemy movement etc
        if(gameStateManager.getCurrentState() == STATES.PLAY) {
            entityManager.update();
            soundManager.stop("pause");
            soundManager.loop("test");
        } else {
            soundManager.stop("test");
            soundManager.play("pause");
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        gameStateManager.draw(graphics2D);

        if(gameStateManager.getCurrentState() == STATES.PLAY) {
            entityManager.draw(graphics2D);
        }

        graphics2D.dispose();
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
    public KeyHandler getKeyHandler() { return keyHandler; }
    public GameStateManager getGameStateManager() { return gameStateManager; }
}