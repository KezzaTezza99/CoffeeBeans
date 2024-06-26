package engine_main;
import engine_main.graphics.Camera;
import engine_main.managers.CollisionManager;
import entity.Enemy;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

/**
 * The game window is responsible for creating a panel that tile manager can draw tiles too to create the game world.
 * Game window will store lots of useful information decided by the user such as
 * tile size
 * scale
 * aspect ratio
 * screen width and height
 * The game window will also be responsible for creating the input manager class and a default player character
 * by default the window will use 16x16 sprites that scale to 48x48 tiles with a max col and row of 16x12 giving an aspect ratio of 4:3.
 * By default, the window will have a black background i.e., doesn't draw any tiles but will provide a moveable player character on the screen
 */
public class GameWindow extends JPanel implements Runnable {
    //TODO: make these static? Can then just access them using GameWindow.tileSize etc, might fix my issues?
    //SCREEN SETTINGS
    //Tile refers to a collection of pixels on screen that sprites etc. will occupy (16x16, 32x32, 64x64)
    final int originalTileSize;                     //Will be the sprites original size i.e., 16x16
    final int scale;                                //Higher resolution monitors will need small sprites to be scaled up
    final int tileSize;                             //The new size of a tile

    //I.e., create a max screen width x height of (16 x 12) which would be the aspect ratio 4:3
    final int maxScreenCol;                         //Max amount of tiles that can be displayed on the X (i.e., 16)
    final int maxScreenRow;                         //Max amount of tiles that can be displayed on the Y (i.e., 12)

    //Using the 16x12 col x row example this would create a screenWidth x screenHeight of (768 x 576)
    final int screenWidth;                          //The width of the game window
    final int screenHeight;                         //The height of the game window

    //TODO: Make private
    //WORLD SETTINGS
    public final int maxWorldCol;                          //The max amount of tiles a world can store on the X
    public final int maxWorldRow;                          //The max amount of tiles a world can store on the Y

    //SCREEN -> WORLD this is where inside the world the screen will be rendered
    public int screenX1, screenX2;
    public int screenY1, screenY2;

    //Using threads to create delta time for the game loop
    Thread gameThread;
    int FPS = 60;

/*
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //          TESTING
    //TODO: Can I do this better?
    //Tile manager to manage the placement of tiles
    public InputHandler input = new InputHandler();                                //Input handler for handling user input
    public Player player = new Player(this, input);                    //Basic player class that will act as a default player controller
    public TileManager tileManager = new TileManager(this);            //Tile manager basically managers a 2D array that stores tiles and data
    //to display in each tile (group of pixels on screen i.e., 16x16)
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
*/

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //          TESTING - new
    //TODO: Can I do this better?
    //Tile manager to manage the placement of tiles
    public InputHandler input;
    public Player player;
    public TileManager tileManager;
    public CollisionManager collisionManager;
    public Enemy enemy;
    public Camera camera;
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //Want more control over these parameters in case a game wants to use different size tiles, aspect ratios etc.
    public GameWindow(int ogTileSize, int scale, int maxScreenCol, int maxScreenRow, int maxWorldCol, int maxWorldRow) {
        //Initialing the screen settings
        this.originalTileSize = ogTileSize;
        this.scale = scale;
        this.tileSize = ogTileSize * scale;

        //Creating the aspect ratio
        this.maxScreenCol = maxScreenCol;
        this.maxScreenRow = maxScreenRow;
        this.screenWidth = tileSize * maxScreenCol;
        this.screenHeight = tileSize * maxScreenRow;

        //Setting the world settings
        this.maxWorldCol = maxWorldCol;
        this.maxWorldRow = maxWorldRow;

        //Default screen inside world
        this.screenX1 = (maxWorldCol / 2) - (maxScreenCol / 2);
        this.screenX2 = (maxWorldCol / 2) + (maxScreenCol / 2);
        this.screenY1 = (maxWorldRow / 2) - (maxScreenRow / 2);
        this.screenY2 = (maxWorldRow / 2) + (maxScreenRow / 2);

        init();

        //Setting up the game window
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(input);
        this.setFocusable(true);

        //System.out.println(STR."The max screen col and row from creation: \{getMaxScreenCol()} \{getMaxScreenRow()}");
    }

    public void init() {
        input = new InputHandler();
        player = new Player(this, input);
        tileManager = new TileManager(this);
        collisionManager = new CollisionManager(this);
        enemy = new Enemy(this);
        camera = new Camera(this);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        //Manipulating system time to create delta time to limit the game to FPS (default 60)
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
                //System.out.printf("FPS: %d%n", drawCount);
                //camera.cameraInfo();
                //camera.simulateYPos(player.y);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    //TODO: WE NEED TO MOVE THE CAMERA DOWN? IT LOOKS LIKE IT IS WORKING BUT HAVE A LOOK TO SEE IF THE NEW WIDTH AND HEIGHT ACTUALLY MOVES TO COVER THE NEW COL / ROW THE PLAYER IS IN
    //THE BOUNDS DEFINITELY STILL GET COLLIDED SO IT HASN'T TRANSLATED

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        //Drawing the tiles
        tileManager.draw(graphics2D);
        //Drawing the player
        player.draw(graphics2D);
        //Drawing the enemy
        //enemy.draw(graphics2D);
        //Disposing of the graphics
        graphics2D.dispose();
    }

    public int getCurrentColumnPosition(int xPos) { return xPos / tileSize; }
    public int getCurrentRowPos(int yPos) { return yPos / tileSize; }

    //Return true if the player is on the bounds otherwise return false
    public boolean isPlayerOnBounds(int currentXPos, int currentYPos) {
        System.out.println(currentYPos);
        if(currentYPos == 0 || currentYPos == (this.maxWorldRow - 1)) {
            return true;
        }
        return false;
    }

    public void recalculateScreenCoordinates() {
        //The player has most likely collided with camera bounds resulting in the world moving, lets re-calculate the screen
        this.screenX1 = (maxWorldCol / 2) - (maxScreenCol / 2);//nope

    }

    //------------------------------------------------------------------------------------------------------------------
    //                          GETTER METHODS
    //------------------------------------------------------------------------------------------------------------------
    public int getOriginalTileSize() {
        return originalTileSize;
    }
    public int getScale() {
        return scale;
    }
    public int getTileSize() {
        return tileSize;
    }
    public int getMaxScreenCol() {
        return this.maxScreenCol;
    }
    public int getMaxScreenRow() {
        return maxScreenRow;
    }
    public int getMaxWorldCol() { return maxWorldCol; }
    public int getMaxWorldRow() { return maxWorldRow; }
    public int getScreenWidth() {
        return screenWidth;
    }
    public int getScreenHeight() {
        return screenHeight;
    }
}