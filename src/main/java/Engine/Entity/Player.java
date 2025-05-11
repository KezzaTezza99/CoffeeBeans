package Engine.Entity;

import Engine.Collisions.AABB;
import Engine.GameContext;
import Engine.Services.EventBusService;
import Engine.GameWindow;
import Engine.Graphics.Camera;
import Engine.Input.KeyHandler;
import Engine.Managers.CollisionManager;
import Engine.Managers.GameStateManager;
import Engine.Managers.TileManager;
import Engine.States.GameState;
import Engine.States.PlayState;
import Engine.States.STATES;
import Game.Events.PlayerTookDamage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GameWindow gameWindow;
    KeyHandler keyHandler;
    TileManager tileManager;

    Camera camera;
    // TODO: THIS IS TEMP
    CollisionManager collisionManager;
    GameContext gameContext;
    //private final AABB futureBounds;
    //!TEMP

    // The players position on screen
    public final int screenX;
    public final int screenY;

    // TEMP - THIS COULD BECOME PART OF ENTITY!
    //TODO: ^
    //Testing UIManager and EventBus
    private int playerHealth = 100;

    public Player(GameWindow gm, KeyHandler kh, GameContext gx) {
        tag = EntityType.PLAYER;
        this.setIsAlive(true);

        this.gameWindow = gm;
        this.keyHandler = kh;
        this.gameContext = gx;

        // Setting the players position to be the centre of the map (screen?) changes if the "world" is bigger
        screenX = (gameWindow.getMaxScreenCol() / 2) * gameWindow.getTileSize();
        screenY = (gameWindow.getMaxScreenRow() / 2) * gameWindow.getTileSize();

        x = screenX;
        y = screenY;

        speed = 4;
        direction = "down";

        entitiesCollisionBox = new AABB(x, y, gameWindow.getTileSize(), gameWindow.getTileSize());
        entitiesFutureBounds = new AABB(x, y, gameWindow.getTileSize(), gameWindow.getTileSize());
        entitiesAggroZone = new AABB(x, y, 256, 256);

//        GameState playState = gsm.states.get(STATES.PLAY);
//
//        // TOD0: 22/04 WHY WAS I DOING THIS?
//        // TODO: Do I really only need tile manager if its play state?
//        // What happens when I want to incorporate more states
//        // Not sure if I like the current creation of objects and the way everything links, but for now its okay
//        if(playState instanceof PlayState) {
//            this.tileManager = ((PlayState) playState).getTileManager();
//        }
//
//        collisionManager = new CollisionManager(tileManager, gameWindow);

        collisionManager = new CollisionManager(gx.getTileManager(), gm);
        tileManager = gx.getTileManager();

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

    @Override
    public void update() {
        if (keyHandler.up.down && (tileManager.canMoveOffScreen || y != 0)) {
            direction = "up";
            entitiesFutureBounds.setX(x);
            entitiesFutureBounds.setY(y - speed);

            if(!collisionManager.willCollide(entitiesFutureBounds)) {
                y -= speed;
            }
        } else if (keyHandler.down.down && (tileManager.canMoveOffScreen || y != (gameWindow.getScreenHeight()) - gameWindow.getTileSize())) {
            direction = "down";
            entitiesFutureBounds.setX(x);
            entitiesFutureBounds.setY(y + speed);

            if(!collisionManager.willCollide(entitiesFutureBounds)) {
                y += speed;
            }
        } else if (keyHandler.left.down && (tileManager.canMoveOffScreen || x != 0)) {
            direction = "left";
            entitiesFutureBounds.setX(x - speed);
            entitiesFutureBounds.setY(y);

            if(!collisionManager.willCollide(entitiesFutureBounds)) {
                x -= speed;
            }
        } else if (keyHandler.right.down && (tileManager.canMoveOffScreen || x != (gameWindow.getScreenWidth()) - gameWindow.getTileSize())) {
            direction = "right";
            entitiesFutureBounds.setX(x + speed);
            entitiesFutureBounds.setY(y);

            if(!collisionManager.willCollide(entitiesFutureBounds)) {
                x += speed;
            }
        }

        // Reset the collision box as it now needs to mimic the users movement
        // TODO: as we made this protected should we actually use a setter
        entitiesCollisionBox.setX(x);
        entitiesCollisionBox.setY(y);
        entitiesAggroZone.setX(x);
        entitiesAggroZone.setY(y);

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
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        BufferedImage image = switch (direction) {
            case "up" -> (spriteNumber == 1) ? up1 : up2;
            case "down" -> (spriteNumber == 1) ? down1 : down2;
            case "left" -> (spriteNumber == 1) ? left1 : left2;
            case "right" -> (spriteNumber == 1) ? right1 : right2;
            default -> null;
        };

        graphics2D.drawImage(image, x, y, gameWindow.getTileSize(), gameWindow.getTileSize(), null);
        entitiesCollisionBox.drawCollider(graphics2D, Color.YELLOW);
        entitiesAggroZone.drawCollider(graphics2D, Color.BLACK);
    }

    @Override
    public void handleCollision(Entity other) {
        if(other.tag == EntityType.ENEMY) {
            this.playerHealth -= 100;
            EventBusService.getBus().post(new PlayerTookDamage(this.playerHealth, this));
        }
    }

    @Override
    public void handleTriggers(Entity other) {}

    public void playerDied() {
        this.setIsAlive(false);
    }
}