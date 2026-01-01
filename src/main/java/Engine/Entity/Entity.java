package Engine.Entity;
import Engine.Collisions.AABB;
import Engine.GameContext;
import Engine.Input.MouseHandler;
import Engine.Services.GameContextService;

import java.awt.*;
import java.awt.image.BufferedImage;

// SUGGESTED THINGS I COULD DO
// Move position x,y to be protected / private and use Getters / Setters
// Could pass gameWindow in the update function if entities need access to the tile map data etc.,
// Could sort entities based on Y coordinates for top-down rendering order to fake depth

public abstract class Entity {
    // Generic Entity Stuff
    protected int x,y;                                  // Stores the entities (x,y) position
    public int worldX, worldY;                          // If the "world" is bigger than the screen this is the players
                                                        // position in relation to the world / map
    public int speed;                                   // Stores the entities movement speed
    public EntityType tag;                              // Useful for knowing what an entity is when handling collision logic

    public int health;                                  // The entities health
    public boolean isAlive = false;                     // Useful for spawning / de-spawning entities

    // Graphics and Animation Stuff
    // TODO: SPRITE SHEETS
    //Stores 10 necessary images to create animated movement for an entity
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, ideal1, ideal2;

    public String direction;                            // Stores information on the current direction an entity is facing
    public int spriteCounter;                           // Used to determine when the next image should be drawn
    public int spriteNumber = 1;
    //public int spriteIndex;                             // The index of the sprite to draw within the sprite-sheet

    // Collision Stuff
    protected AABB entitiesCollisionBox;                // The box that will be used to determine collisions
    public AABB entitiesFutureBounds;                   // Used to stop entities walking into walls etc.,
    public AABB entitiesAggroZone;                      // Will be used for some entities for simple AI behaviour, i.e., chase player
    public AABB entitiesDamageZone;                     // Will be used for some entities for an attack radius / damage radius depending on needs

    public Entity() {
        // TODO: For now defaulting all entities health to 100
        // but in reality would want this to be decided by the programmer
        health = 100;
    }

    public boolean isMouseOver() {
        MouseHandler mouse = GameContextService.get().getMouseHandler();
        return getBounds().mouseIsCollidingWith(mouse.getMouseX(), mouse.getMouseY());
    }

    public AABB getBounds() { return this.entitiesCollisionBox; }
    public AABB getAggroZone() { return this.entitiesAggroZone; }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public void setIsAlive(boolean isAlive) { this.isAlive = isAlive; }
    public boolean getIsAlive() { return this.isAlive; }

    public EntityType getTag() { return this.tag; }

    public abstract void draw(Graphics2D graphics2D);
    public abstract void update();
    public abstract void handleCollision(Entity other);
    public abstract void handleTriggers(Entity other);
}