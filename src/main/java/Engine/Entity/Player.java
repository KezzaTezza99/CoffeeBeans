package Engine.Entity;

import Engine.Collisions.AABB;
import Engine.GameWindow;
import Engine.Input.KeyHandler;
import Engine.Managers.GameStateManager;
import Engine.Managers.TileManager;
import Engine.States.GameState;
import Engine.States.PlayState;
import Engine.States.STATES;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GameWindow gameWindow;
    KeyHandler keyHandler;
    TileManager tileManager;

    // The players position on screen
    public final int screenX;
    public final int screenY;

    public Player(GameWindow gm, KeyHandler kh, GameStateManager gsm) {
        this.gameWindow = gm;
        this.keyHandler = kh;

        // Setting the players position to be the centre of the map (screen?) changes if the "world" is bigger
        screenX = (gameWindow.getMaxScreenCol() / 2) * gameWindow.getTileSize();
        screenY = (gameWindow.getMaxScreenRow() / 2) * gameWindow.getTileSize();

        x = screenX;
        y = screenY;

        speed = 4;
        direction = "down";

        entitiesCollisionBox = new AABB(x, y, 64, 64);

        GameState playState = gsm.states.get(STATES.PLAY);

        // TODO: Do I really only need tile manager if its play state?
        // What happens when I want to incorporate more states
        // Not sure if I like the current creation of objects and the way everything links, but for now its okay
        if(playState instanceof PlayState) {
            this.tileManager = ((PlayState) playState).getTileManager();
        }

        loadPlayerSprite();
    }

    private void loadPlayerSprite() {
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
        // TODO: I shouldn't be able to move off the screen!
        // Okay kinda hacked a solution need to make this neater tho probably TODO
        // get rid of the hard-coded values
        if (keyHandler.up.down && (tileManager.canMoveOffScreen || y != 0)) {
            direction = "up";
            y -= speed;
        } else if (keyHandler.down.down && (tileManager.canMoveOffScreen || y != 1016)) {
            direction = "down";
            y += speed;
        } else if (keyHandler.left.down && (tileManager.canMoveOffScreen || x != 0)) {
            direction = "left";
            x -= speed;
        } else if (keyHandler.right.down && (tileManager.canMoveOffScreen || x != 1856)) {
            direction = "right";
            x += speed;
        }

        // Reset the collision box as it now needs to mimic the users movement
        entitiesCollisionBox.setX(x);
        entitiesCollisionBox.setY(y);

        for(KeyHandler.Key key : keyHandler.keys) {
            if(key.down) {
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
        }
        // TODO: CHECK COLLISIONS
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
        entitiesCollisionBox.drawCollider(graphics2D, Color.GREEN);
        graphics2D.setColor(Color.GREEN);
    }
}