package Engine.Entity;
import Engine.Collisions.AABB;
import Engine.GameContext;
import Engine.Components.Clickable;
import Engine.Services.EventBusService;
import Engine.GameWindow;
import Game.Events.DrawDamageTaken;
import Game.Events.EnemyDied;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Enemy extends Entity implements Clickable {
    GameWindow gameWindow;

    int hp;

    // Movement stuff
    float maxSpeed = 4f;
    float damping = 0.05f;

    // DAMAGE
    private final int DAMAGE_DEALT_TO_PLAYER = 20;

    public Enemy(GameWindow gm, GameContext gx) {
        super(gx);
        tag = EntityType.ENEMY;
        this.setIsAlive(true);

        this.gameWindow = gm;
        this.gameContext = gx;

        // Setting the enemies position to be top left of screen
        x = gameWindow.getHalfScreenWidth();
        y = 128;

        entitiesCollisionBox = new AABB(x, y, gameWindow.getTileSize(), gameWindow.getTileSize());
        entitiesFutureBounds = new AABB(x, y, gameWindow.getTileSize(), gameWindow.getTileSize());
        entitiesAggroZone = new AABB(x, y, 256, 256);
        entitiesDamageZone = new AABB(x, y, 126, 126);

        direction = "down";

        EventBusService.getBus().register(EnemyDied.class, event -> enemyDied());

        EventBusService.getBus().register(DrawDamageTaken.class, event -> {
            gameContext.getUiManager().displayDamageTaken(DAMAGE_DEALT_TO_PLAYER, event.getX(), event.getY(), 250);
        });

        loadEnemySprite();
    }

    // TODO: TEMP -> THIS IS TESTING HAVING 2 ENEMIES, IN REALITY WOULD CHOSE A SPAWN POINT OR MAKE IT RANDOM ETC., BASED
    // ON GAME NEEDS FOR NOW WILL JUST CONSTRUCT MY SECOND ENEMY SLIGHTLY DIFFERENTLY !
    public Enemy(GameWindow gm, GameContext gx, int xPos, int yPos) {
        super(gx);
        tag = EntityType.ENEMY;
        this.setIsAlive(true);

        this.gameWindow = gm;
        this.gameContext = gx;

        x = xPos;
        y = yPos;

        entitiesCollisionBox = new AABB(x, y, gameWindow.getTileSize(), gameWindow.getTileSize());
        entitiesFutureBounds = new AABB(x, y, gameWindow.getTileSize(), gameWindow.getTileSize());
        entitiesAggroZone = new AABB(x, y, 256, 256);
        entitiesDamageZone = new AABB(x, y, 126, 126);

        direction = "down";

        EventBusService.getBus().register(EnemyDied.class, event -> enemyDied());
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
        if(gameContext.getCollisionManager().isCollidingWithTrigger(this.entitiesAggroZone, gameWindow.player.entitiesAggroZone)) updatePosition();
    }

    private void updatePosition() {
        int dx = gameWindow.player.x - this.x;
        int dy = gameWindow.player.y - this.y;
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

        graphics2D.drawImage(image, x, y, gameWindow.getTileSize(), gameWindow.getTileSize(), null);
        entitiesCollisionBox.drawCollider(graphics2D, Color.YELLOW);
        entitiesAggroZone.drawCollider(graphics2D, Color.BLACK);
        entitiesDamageZone.drawCollider(graphics2D, Color.CYAN);
    }

    @Override
    public void handleCollision(Entity other) {}

    @Override
    public void handleTriggers(Entity other) {}

    public void enemyDied() {
        gameWindow.soundManager.play("death");
        gameWindow.soundManager.reset("death");
    }
}