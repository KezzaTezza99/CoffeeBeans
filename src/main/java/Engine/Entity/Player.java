package Engine.Entity;
import Engine.Collisions.AABB;
import Engine.Services.EventBusService;
import Engine.Input.KeyHandler;
import Engine.Services.GameContextService;
import Engine.Utility.GameConstants;
import Game.Events.DamageTaken;
import Game.Events.DrawDamageTaken;
import Game.Events.EntityDied;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    // The players position on screen
    public final int screenX;
    public final int screenY;

    private int hasTakenDamageCooldown = GameConstants.ENEMY_ATTACK_DELAY;
    private boolean startTimer = false;

    public Player() {
        super();
        tag = EntityType.PLAYER;
        this.setIsAlive(true);

        // Setting the players position to be the centre of the map (screen?) changes if the "world" is bigger
        screenX = (GameConstants.MAX_SCREEN_COL / 2) * GameConstants.TILE_SIZE;
        screenY = (GameConstants.MAX_SCREEN_ROW / 2) * GameConstants.TILE_SIZE;

        x = screenX;
        y = screenY;

        speed = 4;
        direction = "down";

        entitiesCollisionBox = new AABB(x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
        entitiesFutureBounds = new AABB(x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
        entitiesAggroZone = new AABB(x, y, 256, 256);

        EventBusService.getBus().register(DrawDamageTaken.class, event -> {
            GameContextService.get().getUiManager().displayDamageTaken(GameConstants.ENEMY_DAMAGE_TO_PLAYER, event.getX(), event.getY(), 250);
        });

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
        if (GameContextService.get().getKeyHandler().up.down
                && (GameContextService.get().getTileManager().canMoveOffScreen || y != 0)) {
            direction = "up";
            entitiesFutureBounds.setX(x);
            entitiesFutureBounds.setY(y - speed);

            if(!GameContextService.get().getCollisionManager().willCollide(entitiesFutureBounds)) {
                y -= speed;
            }
        } else if (GameContextService.get().getKeyHandler().down.down
                && (GameContextService.get().getTileManager().canMoveOffScreen
                || y != (GameConstants.SCREEN_HEIGHT) - GameConstants.TILE_SIZE)) {
            direction = "down";
            entitiesFutureBounds.setX(x);
            entitiesFutureBounds.setY(y + speed);

            if(!GameContextService.get().getCollisionManager().willCollide(entitiesFutureBounds)) {
                y += speed;
            }
        } else if (GameContextService.get().getKeyHandler().left.down
                && (GameContextService.get().getTileManager().canMoveOffScreen || x != 0)) {
            direction = "left";
            entitiesFutureBounds.setX(x - speed);
            entitiesFutureBounds.setY(y);

            if(!GameContextService.get().getCollisionManager().willCollide(entitiesFutureBounds)) {
                x -= speed;
            }
        } else if (GameContextService.get().getKeyHandler().right.down
                && (GameContextService.get().getTileManager().canMoveOffScreen
                || x != (GameConstants.SCREEN_WIDTH) - GameConstants.TILE_SIZE)) {
            direction = "right";
            entitiesFutureBounds.setX(x + speed);
            entitiesFutureBounds.setY(y);

            if(!GameContextService.get().getCollisionManager().willCollide(entitiesFutureBounds)) {
                x += speed;
            }
        }

        // Reset the collision box as it now needs to mimic the users movement
        // TODO: as we made this protected should we actually use a setter
        entitiesCollisionBox.setX(x);
        entitiesCollisionBox.setY(y);
        entitiesAggroZone.setX(x);
        entitiesAggroZone.setY(y);

        for(KeyHandler.Key key : GameContextService.get().getKeyHandler().keys) {
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

        // Count down the time before the player can take damage again
        if(startTimer) {
            hasTakenDamageCooldown--;
        }

        if(startTimer && hasTakenDamageCooldown <= 0) {
            startTimer = false;
            hasTakenDamageCooldown = GameConstants.ENEMY_ATTACK_DELAY;
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

        graphics2D.drawImage(image, (int) x, (int) y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, null);
        entitiesCollisionBox.drawCollider(graphics2D, Color.YELLOW);
        entitiesAggroZone.drawCollider(graphics2D, Color.BLACK);
    }

    // Realistically the cooldown and stuff should be put onto the enemy and not the player
    // this will be an attack cool-down, this will allow us to take damage from many different enemies and not have a
    // 0.5-second delay...
    // We should also some-how trigger this event from the enemies attacking method / trigger...
    @Override
    public void handleCollision(Entity other) {
        if(other.tag == EntityType.ENEMY) {
            if(!startTimer) {
                startTimer = true;
                EventBusService.getBus().post(new DrawDamageTaken((int)x, (int)y));
                EventBusService.getBus().post(new DamageTaken(this, GameConstants.ENEMY_DAMAGE_TO_PLAYER));
                if(this.health <= 0) {
                    EventBusService.getBus().post(new EntityDied(this));
                }
            }
        }
    }

    @Override
    public void handleTriggers(Entity other) {}
}