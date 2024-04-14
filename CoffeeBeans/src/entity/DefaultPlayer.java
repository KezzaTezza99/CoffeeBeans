package entity;

import engine_main.GameWindow;
import engine_main.managers.InputManager;
import engine_main.utilities.Tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Objects;

public class DefaultPlayer extends Entity {
    GameWindow gameWindow;
    InputManager input;

    //The players position on the screen
    public final int playersScreenX;
    public final int playersScreenY;

    public DefaultPlayer(GameWindow gameWindow, InputManager inputManager) {
        this.gameWindow = gameWindow;
        this.input = inputManager;

        //Setting the players position to the centre of the screen
        playersScreenX = gameWindow.screenWidth / 2 - (gameWindow.tileSize / 2);
        playersScreenY = gameWindow.screenHeight / 2 - (gameWindow.tileSize / 2);

        //Setting the entities (player) world position to this location in the (world) map (centre of the map)
        //entitiesWorldX = (gameWindow.maxWorldCol  * gameWindow.tileSize) / 2;
        //entitiesWorldY = (gameWindow.maxWorldRow * gameWindow.tileSize) / 2;

        entitiesWorldX = 8 * gameWindow.tileSize;
        entitiesWorldY = 6 * gameWindow.tileSize;
        //Setting some default entity parameters
        speed = 4;
        direction = "down";

        //Initializing the collidable area

        //Loading the images that will be used for the default player character
        getPlayerImage();
    }

    public void getPlayerImage() {
        up1 = setupPlayerData("boy_up_1");
        up2 = setupPlayerData("boy_up_2");
        down1 = setupPlayerData("boy_down_1");
        down2 = setupPlayerData("boy_down_2");
        left1 = setupPlayerData("boy_left_1");
        left2 = setupPlayerData("boy_left_2");
        right1 = setupPlayerData("boy_right_1");
        right2 = setupPlayerData("boy_right_2");
    }

    public BufferedImage setupPlayerData(String imageName) {
        Tools tools = new Tools();
        BufferedImage image = null;


        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/" + imageName + ".png")));
            image = tools.scaleAImage(image, gameWindow.tileSize, gameWindow.tileSize);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void update() {
        //If the player is pressing any key then we should run the "animation cycle"
        if (input.upPressed || input.downPressed || input.leftPressed || input.rightPressed) {
            //Set the players direction based on player input
            if (input.upPressed) {
                direction = "up";
            } else if (input.downPressed) {
                direction = "down";
            } else if (input.leftPressed) {
                direction = "left";
            } else {
                direction = "right";
            }

            //Checking tile collision

            //Check object collision

            //If the tile isn't collidable then move the player in the intended direction
            if(!isCollidable) {
                switch (direction) {
                    case "up":
                        entitiesWorldY -= speed;
                        break;
                    case "down":
                        entitiesWorldY += speed;
                        break;
                    case "left":
                        entitiesWorldX -= speed;
                        break;
                    case "right":
                        entitiesWorldX += speed;
                        break;
                }
            }

            //Basic attempt at animating a sprite i.e., over time just swap from image 1 to image 2 to give the illusion of animation
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        //Render a different image based on the sprite number (image 1 or 2 for the current direction)
        BufferedImage image = switch (direction) {
            case "up" -> (spriteNumber == 1) ? up1 : up2;
            case "down" -> (spriteNumber == 1) ? down1 : down2;
            case "left" -> (spriteNumber == 1) ? left1 : left2;
            case "right" -> (spriteNumber == 1) ? right1 : right2;
            default -> null;
        };

        //X and Y will normally be these values however, sometimes will need to offset it to stop the camera at the world edges
        int x = playersScreenX;
        int y = playersScreenY;

        if(playersScreenX > entitiesWorldX) {
            x = entitiesWorldX;
        }

        if(playersScreenY > entitiesWorldY) {
            y = entitiesWorldY;
        }

        //TODO: The code below as well as the same code in TileManager could be added to Util to keep code DRY?
        int rightOffset = gameWindow.screenWidth - playersScreenX;
        if(rightOffset > gameWindow.worldWidth - entitiesWorldX) {
            x = gameWindow.screenWidth - (gameWindow.worldWidth - entitiesWorldX);
        }
        int bottomOffset = gameWindow.screenHeight - playersScreenY;
        if(bottomOffset > gameWindow.worldHeight - entitiesWorldY) {
            y = gameWindow.screenHeight - (gameWindow.worldHeight - entitiesWorldY);
        }

        //Drawing the player to the screen
        graphics2D.drawImage(image, x, y, null);
    }
}
