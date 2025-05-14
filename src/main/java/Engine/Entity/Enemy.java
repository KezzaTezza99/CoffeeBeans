package Engine.Entity;
import Engine.Collisions.AABB;
import Engine.GameContext;
import Engine.Input.Clickable;
import Engine.Services.EventBusService;
import Engine.GameWindow;
import Game.Events.DrawHP;
import Game.Events.EnemyDied;
import Game.Events.EnemyTookDamage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Enemy extends Entity implements Clickable {
    GameWindow gameWindow;
    
    int hp;
    int damage = 50;
    int xMousePos, yMousePos;

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

        speed = 4;
        direction = "down";

        EventBusService.getBus().register(EnemyDied.class, event -> enemyDied());

        EventBusService.getBus().register(DrawHP.class, event -> {
            this.hp = event.getHp();
            gameContext.getUiManager().displayHP(this.hp, event.getX(), event.getY());
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

        speed = 4;
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
//        if(other.tag == EntityType.PLAYER) {
//            // TODO: Attack the player
//            EventBusService.getBus().post(new EnemyDied());
//            gameWindow.entityManager.removeEntity(this);
//        }
    }

    @Override
    public void handleTriggers(Entity other) {

    }

    public void enemyDied() {
        gameWindow.soundManager.play("death");
        gameWindow.soundManager.reset("death");
    }

    @Override
    public void onClick() {
        this.health -= damage;
        this.xMousePos = gameContext.getMouseHandler().getMouseX();
        this.yMousePos = gameContext.getMouseHandler().getMouseY();

        EventBusService.getBus().post(new EnemyTookDamage(this.health, this));
        EventBusService.getBus().post(new DrawHP(damage, xMousePos, yMousePos));
    }
}