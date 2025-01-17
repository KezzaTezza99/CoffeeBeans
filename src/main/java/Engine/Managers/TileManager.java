package Engine.Managers;

import Engine.GameWindow;
import Engine.Graphics.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
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
        mapSpriteData = new int[gameWindow.getMaxScreenRow()][gameWindow.getMaxScreenCol()];

        // Load sprite(s)
        loadSprites();
        // Load world
        // WE NEED ERROR HANDLING FOR INCORRECT WORLDS AND STUFF TODO
        // IF ITS INCORRECT RIGHT NOW IT WILL JUST DISPLAY THE FIRST IMAGE IN THE ARRAY
        // THIS COULD JUST BECOME A DEFAULT TEXTURE THEN LOG A MESSAGE?
        loadWorldFromFile("/worlds/test-world-2.txt");
    }

    public void loadSprites() {
        try {
            sprites[0] = new Sprite();
            sprites[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/test2.png")));

            sprites[1] = new Sprite();
            sprites[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/test3.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadWorldFromFile(String filePath) {
        // Try loading the data
        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));

            // X , Y
            int row = 0;
            int column = 0;

            while(row < gameWindow.getMaxScreenRow() && column < gameWindow.getMaxScreenCol()) {
                // Reading the text file line by line while it's within limits of the screen size we have defined
                String line = bufferedReader.readLine();

                while (row < gameWindow.getMaxScreenRow()) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[row]);

                    mapSpriteData[row][column] = num;
                    row++;
                }

                if (row == gameWindow.getMaxScreenRow()) {
                    row = 0;
                    column++;
                }
            }
            System.out.println(Arrays.deepToString(mapSpriteData));
            bufferedReader.close();
        } catch(Exception ignored) {}
    }

    public void draw(Graphics2D graphics2D) {
        // The tile at (x,y) on screen
        int x = 0;
        int y = 0;

        // Loop through the mapSpriteData array to draw the tiles
        for(int row = 0; row < gameWindow.getMaxScreenRow(); row++) {
            for(int col = 0; col < gameWindow.getMaxScreenCol(); col++) {
                // The sprite at the specified point on the row and column
                int sprite = mapSpriteData[row][col];

                graphics2D.drawImage(sprites[sprite].image, x * gameWindow.getTileSize(), y * gameWindow.getTileSize(), gameWindow.getTileSize(), gameWindow.getTileSize(), null);
                x++;

                if(x == gameWindow.getMaxScreenRow()) {
                    x = 0;
                    y++;
                }
            }
        }
    }
}