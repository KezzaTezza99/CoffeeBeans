package Engine;
import Engine.Input.KeyHandler;
import Engine.Managers.GameStateManager;

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

//    private TileManager tileManager;
//    private KeyHandler keyHandler;
    private GameStateManager gameStateManager;

    public GameWindow() {
        // Setting up the game window
        this.setPreferredSize(new Dimension(1920,1080));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        tileSize = 64;
        maxScreenCol = 30;
        maxScreenRow = 16;
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
//        tileManager = new TileManager(this);
//        keyHandler = new KeyHandler(this);
        gameStateManager = new GameStateManager(this);
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
//                input(keyHandler);
                delta--;
            }
        }
    }

    public void update() {
        gameStateManager.update();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        gameStateManager.draw(graphics2D);
        graphics2D.dispose();
    }

    // TODO: THIS COULD PROBABLY BE REMOVED AND JUST KEPT INTERNALLY IN GSM
    public void input(KeyHandler keyHandler) {
        gameStateManager.input(keyHandler);
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
}