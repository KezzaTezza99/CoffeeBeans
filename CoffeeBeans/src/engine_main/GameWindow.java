package engine_main;

import engine_main.managers.InputManager;
import engine_main.managers.TileManager;
import entity.DefaultPlayer;

import javax.swing.*;
import java.awt.*;

/* TODO: Max world isn't necessarily the actual size of the map, i.e., right now im centering the player to the middle
*   of the max screen size (50x50) but the map is (16x12) so I would expect to be in the middle (8x6) instead I am
*   being placed in the centre (25x25). This also means that the world could be a smaller size yet the engine is rendering
*   extra tiles by ensuring that it draws the full max width and height
* */
public class GameWindow extends JPanel implements Runnable {

    //SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //WORLD SETTINGS
    public final int maxWorldCol = 16;
    public final int maxWorldRow = 12;

    //KINDA CAMERA STUFF
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //PERFORMANCE
    Thread gameThread;
    int FPS = 60;

    //MANAGERS
    TileManager tileManager = new TileManager(this);
    InputManager inputManager = new InputManager();


    //PLAYER
    public DefaultPlayer defaultPlayer = new DefaultPlayer(this, inputManager);

    public GameWindow() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputManager);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        //Game loop
        while(gameThread != null) {
            //Getting system time to restrict the game to FPS
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                //Updating game data
                update();
                //Drawing game data
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.printf("FPS: %d%n", drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        defaultPlayer.update();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        //Want more control over 2D graphics so casting graphics to 2DGraphics
        Graphics2D graphics2D = (Graphics2D) graphics;

        //Drawing the tiles
        tileManager.draw(graphics2D);

        //Drawing the player
        defaultPlayer.draw(graphics2D);

        //Disposing of the graphics object
        graphics2D.dispose();
    }
}