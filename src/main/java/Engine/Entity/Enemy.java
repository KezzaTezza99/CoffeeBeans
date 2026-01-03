package Engine.Entity;
import Engine.Collisions.AABB;
import Engine.GameContext;
import Engine.Components.Clickable;
import Engine.Services.EventBusService;
import Engine.GameWindow;
import Engine.Services.GameContextService;
import Engine.Utility.GameConstants;
import Game.Events.DrawDamageTaken;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Enemy extends Entity implements Clickable {
    // Movement stuff
    float maxSpeed = 4f;
    float damping = 0.05f;

    public Enemy() {
        super();
        tag = EntityType.ENEMY;
        this.setIsAlive(true);

        // Setting the enemies position to be top left of screen
        x = GameConstants.HALF_SCREEN_WIDTH;
        y = 128;

        entitiesCollisionBox = new AABB(x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
        entitiesFutureBounds = new AABB(x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
        entitiesAggroZone = new AABB(x, y, 256, 256);
        entitiesDamageZone = new AABB(x, y, 126, 126);

        direction = "down";

        loadEnemySprite();
    }

    // TODO: TEMP -> THIS IS TESTING HAVING 2 ENEMIES, IN REALITY WOULD CHOSE A SPAWN POINT OR MAKE IT RANDOM ETC., BASED
    // ON GAME NEEDS FOR NOW WILL JUST CONSTRUCT MY SECOND ENEMY SLIGHTLY DIFFERENTLY !
    public Enemy(int xPos, int yPos) {
        super();
        tag = EntityType.ENEMY;
        this.setIsAlive(true);


        x = xPos;
        y = yPos;

        entitiesCollisionBox = new AABB(x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
        entitiesFutureBounds = new AABB(x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
        entitiesAggroZone = new AABB(x, y, 256, 256);
        entitiesDamageZone = new AABB(x, y, 126, 126);

        direction = "down";

        loadEnemySprite();
    }

    private void loadEnemySprite() {
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
        if(GameContextService.get().getCollisionManager().isCollidingWithTrigger(
                this.entitiesAggroZone, GameContextService.get().getGameWindow().player.entitiesAggroZone)
        ) updatePosition();
    }

    private void updatePosition() {
        int dx = GameContextService.get().getGameWindow().player.x - this.x;
        int dy = GameContextService.get().getGameWindow().player.y - this.y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        // Get the direction needed for the sprite
        if(Math.abs(dx) > Math.abs(dy)) {
            if(dx > 0) direction = "right";
            else direction = "left";
        } else {
            if(dy > 0) direction = "down";
            else direction = "up";
        }

        // Slow the enemy down gradually if closing in on the player
        if(distance > 0.01f) {
            float dirX = dx / distance;
            float dirY = dy / distance;

            // Slow down the closer we get
            speed = (int) Math.min(maxSpeed, distance * damping);

            this.x += (int) (dirX * speed);
            this.y += (int) (dirY * speed);
        }

        // Finally move the entities collision boxes
        updateAllAABBs();

        // "Animate" the sprite by rotating through the sprites
        spriteCounter++;

        if(spriteCounter > 10) {
            if(spriteNumber == 1) spriteNumber = 2;
            else spriteNumber = 1;

            spriteCounter = 0;
        }
    }

    private void updateAllAABBs() {
        this.entitiesCollisionBox.setX(x);
        this.entitiesCollisionBox.setY(y);
        this.entitiesAggroZone.setX(x);
        this.entitiesAggroZone.setY(y);
        this.entitiesDamageZone.setX(x);
        this.entitiesDamageZone.setY(y);
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

        graphics2D.drawImage(image, x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, null);
        entitiesCollisionBox.drawCollider(graphics2D, Color.YELLOW);
        entitiesAggroZone.drawCollider(graphics2D, Color.BLACK);
        entitiesDamageZone.drawCollider(graphics2D, Color.CYAN);
    }

    @Override
    public void handleCollision(Entity other) {}

    @Override
    public void handleTriggers(Entity other) {}

    @Override
    public void onClick(Entity entity) {
        if(GameContextService.get().getCollisionManager().withinDamageRangeAndMouseIsIntersecting(
                this, entitiesAggroZone, GameContextService.get().getGameWindow().player.entitiesAggroZone)) {
            EventBusService.getBus().register(DrawDamageTaken.class, event -> {
                GameContextService.get().getUiManager().displayDamageTaken(GameConstants.PLAYER_DAMAGE_TO_ENEMY, event.getX(), event.getY(), 250);
            });
            handleClickEvent(this);
        }
    }

    public static void enemyDied() {
        GameContextService.get().getSoundManager().play("death");
        GameContextService.get().getSoundManager().reset("death");
    }
}