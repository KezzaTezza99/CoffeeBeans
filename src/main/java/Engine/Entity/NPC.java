package Engine.Entity;
import Engine.Collisions.AABB;
import Engine.GameContext;
import Engine.GameWindow;
import Engine.Services.EventBusService;
import Engine.Services.GameContextService;
import Engine.States.STATES;
import Engine.Utility.GameConstants;
import Game.Events.ShowDialog;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class NPC extends Entity {
    public boolean hasTriggered = false;

    public NPC() {
        super();
        this.tag = EntityType.NPC;
        this.setIsAlive(true);
        this.x = 300;
        this.y = 700;

        entitiesCollisionBox = new AABB(x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
        entitiesFutureBounds = new AABB(x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
        entitiesAggroZone = new AABB(x, y, 256, 256);

        direction = "left";

        EventBusService.getBus().register(ShowDialog.class, event -> showDialog());

        // TODO: Become an override or default method inside Entity??
        loadSprite();
    }

    private void loadSprite() {
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
    public void update() {}

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
    }

    @Override
    public void handleCollision(Entity other) {

    }

    @Override
    public void handleTriggers(Entity other) {
        if(other.tag == EntityType.PLAYER) {
            if(GameContextService.get().getCollisionManager().isCollidingWithTrigger(this.entitiesAggroZone, other.getAggroZone())) {
                if(!hasTriggered) {
                    hasTriggered = true;
                    EventBusService.getBus().post(new ShowDialog());
                }
            }
        }
    }

    private void showDialog() {
        // Change the game state
        GameContextService.get().getGameWindow().getGameStateManager().blockInputForCurrentState(true);
        GameContextService.get().getGameWindow().getGameStateManager().setGameStateIsActive(STATES.DIALOG, true);
    }

    public EntityType getType() { return this.tag; }
}