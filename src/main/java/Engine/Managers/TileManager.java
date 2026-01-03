package Engine.Managers;
import Engine.GameWindow;
import Engine.Graphics.Sprite;
import Engine.Services.GameContextService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Tile Manager is responsible for drawing all the sprites to the screen and storing all the data
 * that will make up the game world. It will read in a text file and draw the corresponding sprites*/
public class TileManager {
    // Information on the sprites
    public Sprite[] sprites;                    // Stores all the sprites that will be used in the game
    public int[][] mapSpriteData;               // Stores the actual sprite that represent an (X,Y) map coordinate

    public boolean canMoveOffScreen;

    // TEMP SOLUTION TO DRAWING PAUSE MENU
    public boolean drawPauseMenu = false;

    // TODO: PROBABLY SHOULD PASS IN THE MAP TO LOAD AND ALSO ADD MORE CUSTOMIZATION HERE
    // i.e., will the map be full-screen, can you have the concept of a bigger world all this jazz
    public TileManager(boolean canMoveOffScreen) {
        // TODO: Should probably decide on size of this array based on an input folder for sprite?
        // TODO: Should def introduce the idea of spritesheets instead!
        sprites = new Sprite[40];

        // Initialise the map data to be the max size of the screen
        // TODO: The GameContext is not fully initialised when we make calls to the Game Data here so maybe
        // the fix is to implement these magic numbers into the Global Constants instead
        mapSpriteData = new int[30][17];

        this.canMoveOffScreen = canMoveOffScreen;

        // Load sprite(s)
        loadSprites();
        // Load world
        loadWorldFromFile("/worlds/test-world.txt");
    }

    public void loadSprites() {
        try {
            // TODO: Could we work with sprite-sheets instead?
            // TODO: also far to repetitive code we could make this recursive for an entire folder path
            sprites[0] = new Sprite();
            sprites[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/sprites/grass/base-01.png")));

            sprites[1] = new Sprite();
            sprites[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/sprites/grass/base-02.png")));

            sprites[2] = new Sprite();
            sprites[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/test3.png")));

            sprites[3] = new Sprite();
            sprites[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/test4.png")));
            sprites[3].isCollidable = true;

            sprites[4] = new Sprite();
            sprites[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/sprites/house-01.png")));
            sprites[4].isCollidable = true;

            sprites[5] = new Sprite();
            sprites[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/sprites/house-02.png")));
            sprites[5].isCollidable = true;

            sprites[6] = new Sprite();
            sprites[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/sprites/grass/vertical-01.png")));

            sprites[7] = new Sprite();
            sprites[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/sprites/grass/horizontal-01.png")));

            sprites[8] = new Sprite();
            sprites[8].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/sprites/grass/cross-roads-01.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: Lots of magic numbers
    public void loadWorldFromFile(String filePath) {
        // Try loading the data
        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));

            int x = 0;
            int y = 0;

            // TODO: Works but could be more optimised
            while(x < 30 && y < 17) {
                String line = bufferedReader.readLine();

                while(x < 30) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[x]);
                    mapSpriteData[x][y] = num;
                    x++;
                }

                if(x == 30) {
                    x = 0;
                    y++;
                }
            }
            bufferedReader.close();
        } catch(Exception ignored) {}
    }

    public void draw(Graphics2D graphics2D) {
        for (int column = 0; column < 30; column++) {
            for (int row = 0; row < 17; row++) {
                int sprite = mapSpriteData[column][row];

                int xPos = column * 64;
                int yPos = row * 64;

                graphics2D.drawImage(sprites[sprite].image,
                        xPos,
                        yPos,
                        64,
                        64,
                        null);
            }
        }
    }

    public void setDrawPauseMenu(boolean drawPauseMenu) {
        this.drawPauseMenu = drawPauseMenu;
    }
}