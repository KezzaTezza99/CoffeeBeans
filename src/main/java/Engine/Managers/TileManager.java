package Engine.Managers;

import Engine.GameWindow;
import Engine.Graphics.Sprite;

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
    // Will need access to the game window to draw the tiles to the screen
    GameWindow gameWindow;

    // Information on the sprites
    public Sprite[] sprites;                    // Stores all the sprites that will be used in the game
    // TODO: ^ Have a sprite manager that maybe reads in a sprite sheet and builds the correct sized array etc?
    public int[][] mapSpriteData;

    public TileManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        sprites = new Sprite[3];

        // Initialise the map data to be the max size of the screen
        mapSpriteData = new int[gameWindow.getMaxScreenCol()][gameWindow.getMaxScreenRow()];

        // Load sprite(s)
        loadSprites();
        // Load world
        // WE NEED ERROR HANDLING FOR INCORRECT WORLDS AND STUFF TODO
        // IF ITS INCORRECT RIGHT NOW IT WILL JUST DISPLAY THE FIRST IMAGE IN THE ARRAY
        // THIS COULD JUST BECOME A DEFAULT TEXTURE THEN LOG A MESSAGE?
        loadWorldFromFile("/worlds/test-world.txt");
    }

    public void loadSprites() {
        try {
            sprites[0] = new Sprite();
            sprites[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/test.png")));

            sprites[1] = new Sprite();
            sprites[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/test2.png")));

            sprites[2] = new Sprite();
            sprites[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/test3.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadWorldFromFile(String filePath) {
        // Try loading the data
        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));

            int x = 0;
            int y = 0;

            // TODO: Works but could be more optimised
            while(x < gameWindow.getMaxScreenCol() && y < gameWindow.getMaxScreenRow()) {
                String line = bufferedReader.readLine();

                while(x < gameWindow.getMaxScreenCol()) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[x]);
                    mapSpriteData[x][y] = num;
                    x++;
                }

                if(x == gameWindow.getMaxScreenCol()) {
                    x = 0;
                    y++;
                }
            }
            bufferedReader.close();
        } catch(Exception ignored) {}
    }

    public void draw(Graphics2D graphics2D) {
        for (int column = 0; column < gameWindow.getMaxScreenCol(); column++) {
            for (int row = 0; row < gameWindow.getMaxScreenRow(); row++) {
                int sprite = mapSpriteData[column][row];

                int xPos = column * gameWindow.getTileSize();
                int yPos = row * gameWindow.getTileSize();

                graphics2D.drawImage(sprites[sprite].image, xPos, yPos, gameWindow.getTileSize(), gameWindow.getTileSize(), null);
            }
        }
    }
}