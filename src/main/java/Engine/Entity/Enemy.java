package Engine.Entity;

import Engine.Collisions.AABB;
import Engine.GameWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Enemy extends Entity {
    GameWindow gameWindow;

    public Enemy(GameWindow gm) {
        tag = EntityType.ENEMY;

        this.gameWindow = gm;

        // Setting the enemies position to be top left of screen
        x = gameWindow.getHalfScreenWidth();
        y = 128;

        entitiesCollisionBox = new AABB(x, y, gameWindow.getTileSize(), gameWindow.getTileSize());
        entitiesFutureBounds = new AABB(x, y, gameWindow.getTileSize(), gameWindow.getTileSize());
        entitiesAggroZone = new AABB(x, y, 256, 256);

        speed = 4;
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

    public void update() {

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
        entitiesCollisionBox.drawCollider(graphics2D, Color.YELLOW);
        entitiesAggroZone.drawCollider(graphics2D, Color.BLACK);
    }

    public void handleCollision(Entity other) {
        if(other.tag == EntityType.PLAYER) {
            // TODO: Attack the player
            System.out.println("The enemy would be attacking the player right now");
        }
    }
}
