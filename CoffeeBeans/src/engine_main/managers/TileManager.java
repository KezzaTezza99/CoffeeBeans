package engine_main.managers;

import engine_main.GameWindow;
import engine_main.utilities.Tools;
import tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;

public class TileManager {
    GameWindow gameWindow;
    public Tile[] tiles;
    public int[][] mapTileData;

    public TileManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        //TODO: Dynamically get the size of this array? - Read resource file?
        //Setting the size of the tile array (which stores types of tiles)
        tiles = new Tile[10];
        //TODO: Would actually set the size of this array to be the size of the map file provided. This should fix the issue with centering to world space
        //Setting the size of the array that will hold the tile data for each tile (group of pixels) in the world
        mapTileData = new int[gameWindow.maxWorldCol][gameWindow.maxWorldRow];

        //Load the tile images from the resource folder
        getTileImages();
        //Load the game world map text file
        getMapData("/maps/map01.txt");
    }

    //TODO: Use a JSON file that sets these params? - Could also do it after the todo in constructor?
    public void getTileImages() {
        setupTileData(0, "grass", false);
        setupTileData(1, "tree", true);
    }

    public void setupTileData(int index, String imagePath, boolean collision) {
        //TODO: Can use a static method instead? Temp class that can defiantly be implemented better and with more functionality
        Tools tools = new Tools();

        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imagePath + ".png")));
            tiles[index].image = tools.scaleAImage(tiles[index].image, gameWindow.tileSize, gameWindow.tileSize);
            tiles[index].collision = collision;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void getMapData(String filePath) {

        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));

            int col = 0;
            int row = 0;

            while(col < gameWindow.maxWorldCol && row < gameWindow.maxWorldRow) {
                //Reading the text file while it's in within limits of the set world size
                String line = bufferedReader.readLine();

                while(col < gameWindow.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileData[col][row] = num;
                    col++;
                }
                if(col == gameWindow.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();
        } catch(Exception ignored) { }
    }

    public void draw(Graphics2D graphics2D) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gameWindow.maxWorldCol && worldRow < gameWindow.maxWorldRow) {
            int tileNum = mapTileData[worldCol][worldRow];

            //First we check
            int worldX = worldCol * gameWindow.tileSize;
            int worldY = worldRow * gameWindow.tileSize;
            int screenX = worldX - gameWindow.defaultPlayer.entitiesWorldX + gameWindow.defaultPlayer.playersScreenX;
            int screenY = worldY - gameWindow.defaultPlayer.entitiesWorldY + gameWindow.defaultPlayer.playersScreenY;

            //TODO: This should be optional behaviour for the user not every game will want the camera to stop at the borders of the world - i.e., could want the user to be stopped at a certain distance
            //but they show more surrounding world etc.
            //TODO: Maybe I should have a camera class that does all this stuff for me
            //Stopping the camera before it escapes world bounds
            if(gameWindow.defaultPlayer.playersScreenX > gameWindow.defaultPlayer.entitiesWorldX) {
                screenX = worldX;
            }

            if(gameWindow.defaultPlayer.playersScreenY > gameWindow.defaultPlayer.entitiesWorldY) {
                screenY = worldY;
            }

            //To stop the camera on the right side of the map need to offset the players width beforehand
            int rightOffset = gameWindow.screenWidth - gameWindow.defaultPlayer.playersScreenX;
            if(rightOffset > gameWindow.worldWidth - gameWindow.defaultPlayer.entitiesWorldX) {
                screenX = gameWindow.screenWidth - (gameWindow.worldWidth - worldX);
            }
            int bottomOffset = gameWindow.screenHeight - gameWindow.defaultPlayer.playersScreenY;
            if(bottomOffset > gameWindow.worldHeight - gameWindow.defaultPlayer.entitiesWorldY) {
                screenY = gameWindow.screenHeight - (gameWindow.worldHeight - worldY);
            }

            //TODO: Take a look at this and see if I can do it better
            //Only draw tiles we can see
            if (worldX + gameWindow.tileSize > gameWindow.defaultPlayer.entitiesWorldX - gameWindow.defaultPlayer.playersScreenX &&
                    worldX - gameWindow.tileSize < gameWindow.defaultPlayer.entitiesWorldX + gameWindow.defaultPlayer.playersScreenX &&
                    worldY + gameWindow.tileSize > gameWindow.defaultPlayer.entitiesWorldY - gameWindow.defaultPlayer.playersScreenY &&
                    worldY - gameWindow.tileSize < gameWindow.defaultPlayer.entitiesWorldY + gameWindow.defaultPlayer.playersScreenY) {
                graphics2D.drawImage(tiles[tileNum].image, screenX, screenY, null);
            } else if(gameWindow.defaultPlayer.playersScreenX > gameWindow.defaultPlayer.entitiesWorldX ||
                        gameWindow.defaultPlayer.playersScreenY > gameWindow.defaultPlayer.entitiesWorldY ||
                        rightOffset > gameWindow.worldWidth - gameWindow.defaultPlayer.entitiesWorldX ||
                        bottomOffset > gameWindow.worldHeight - gameWindow.defaultPlayer.entitiesWorldY) {
                graphics2D.drawImage(tiles[tileNum].image, screenX, screenY, null);
            }
            worldCol++;

            if (worldCol == gameWindow.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}