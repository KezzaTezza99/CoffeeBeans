package entity;

import engine_main.GameWindow;
import engine_main.InputHandler;
import engine_main.physics.AABB;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

//TODO: Pass a lot of the info in as params? So creating a player can decide on unique settings
//TODO: The way im handling collisions and stuff is messy I could defo do with refactoring all of it!!!

public class Player extends Entity {
    GameWindow gameWindow;
    InputHandler input;

    //The players position on the screen
    public final int screenX;
    public final int screenY;

    public Player(GameWindow gameWindow, InputHandler inputHandler) {
        this.gameWindow = gameWindow;
        this.input = inputHandler;

        //Setting the screen to the centre of a map (centre of a screen and centre of that tile)
        screenX = gameWindow.getScreenWidth() / 2;
        screenY = gameWindow.getScreenHeight() / 2;

        //Setting default starting position for the player in the world (which is centred to the screen and also centre of the map)
        x = screenX;
        y = screenY;
        worldX = ((gameWindow.maxWorldCol / 2) * gameWindow.getTileSize());
        worldY = ((gameWindow.maxWorldRow / 2) * gameWindow.getTileSize());

        //x = gameWindow.maxWorldCol / 2;
        //y = gameWindow.maxWorldRow / 2;

        //Setting the players position to be correct to the tile it's starting in
        tilePosScreenX = x / gameWindow.getTileSize();
        tileScreenPosY = y / gameWindow.getTileSize();
        tileWorldPosX = worldX / gameWindow.getTileSize();
        tileWorldPosY = worldY / gameWindow.getTileSize();

        //Setting players speed and default starting position
        speed = 4;
        direction = "down";

        collisionBox = new AABB(x, y, 32, 32);

        System.out.printf("Player INFO: x: %d y: %d world x: %d world y: %d tile x: %d tile y: %d%n", x, y, worldX, worldY, tilePosScreenX, tileScreenPosY);

        //Setting the player to be the centre of the screen
        loadPlayerSprites();
    }

    public void loadPlayerSprites() {
        try {
            //Try loading the player sprites
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_2.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (input.upPressed && canMoveUp) {
            direction = "up";
            y -= speed;
            worldY -= speed;
            collisionBox.setY(y);
        } else if (input.downPressed && canMoveDown) {
            direction = "down";
            y += speed;
            worldY += speed;
            collisionBox.setY(y);
        } else if (input.leftPressed && canMoveLeft) {
            direction = "left";
            x -= speed;
            tilePosScreenX = x / gameWindow.getTileSize();
            collisionBox.setX(x);
        } else if (input.rightPressed && canMoveRight) {
            direction = "right";
            x += speed;
            tilePosScreenX = x / gameWindow.getTileSize();
            collisionBox.setX(x);
        }

        //Checking collisions
        gameWindow.collisionManager.checkEntity(this);
        gameWindow.collisionManager.tileIsFree(this);
        playerHasHitCamBounds();

        //Way of animating could come up with a smarter and better way of doing this
        if(input.isKeyPressed) {
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        }

        //TODO: Set these with the player movement inside the if statements
        //collisionBox.setX(x);
        //collisionBox.setY(y);

        tilePosScreenX = x / gameWindow.getTileSize();
        tileScreenPosY = y / gameWindow.getTileSize();
    }

    //TODO: Could probably just have one method that checks all the bounds instead but this is fine for testing rn
    public void playerHasHitCamBounds() {
        System.out.printf("Outside IF Screen Y1: %d Screen Y2: %d %n", gameWindow.screenY1, gameWindow.screenY2);
        //As the player is about to reach the top of the screen we should move the world downwards to give the illusion they've moved up the world?
        if(gameWindow.camera.hasHitUpperBounds(tileScreenPosY)) {
            //Changing the y position of the rendered world
            gameWindow.screenY1 -= 6;
            gameWindow.screenY2 -= 6;
            System.out.printf("Inside IF Screen Y1: %d Screen Y2: %d %n", gameWindow.screenY1, gameWindow.screenY2);
            //The player should be at the bottom of the map in the final tile
            gameWindow.player.y = gameWindow.getHeight() - gameWindow.getTileSize();
        }

        if(gameWindow.camera.hasHitLowerBounds(tileScreenPosY)) {
            //Changing the y position of the rendered world
            gameWindow.screenY1 += 6;
            gameWindow.screenY2 += 6;
            //Moving the player to be back in the centre of the screen
            gameWindow.player.y = gameWindow.getHeight() / 2;
        } else if(gameWindow.camera.hasHitLeftBounds(tilePosScreenX)) {
            //Changing the x position of the rendered world
            gameWindow.screenX1 -= 8;
            gameWindow.screenX2 -= 8;
            //Moving the player to be back in the centre of the screen
            gameWindow.player.x = gameWindow.getWidth() / 2;
        } else if(gameWindow.camera.hasHitRightBounds(tilePosScreenX)) {
            //Changing the x position of the rendered world
            gameWindow.screenX1 += 8;
            gameWindow.screenX2 += 8;
            //Moving the player to be back in the centre of the screen
            gameWindow.player.x = gameWindow.getWidth() / 2;
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = switch (direction) {
            case "up" -> (spriteNumber == 1) ? up1 : up2;
            case "down" -> (spriteNumber == 1) ? down1 : down2;
            case "left" -> (spriteNumber == 1) ? left1 : left2;
            case "right" -> (spriteNumber == 1) ? right1 : right2;
            default -> null;
        };
        graphics2D.drawImage(image, x, y, gameWindow.getTileSize(), gameWindow.getTileSize(), null);
        collisionBox.drawCollider(graphics2D, Color.GREEN);
        graphics2D.setColor(Color.GREEN);
    }
}