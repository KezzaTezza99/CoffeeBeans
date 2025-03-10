package Engine.Entity;

import Engine.Collisions.AABB;

import java.awt.image.BufferedImage;

public class Entity {
    // Generic Entity Stuff
    public int x,y;                                     // Stores the entities (x,y) position
    public int worldX, worldY;                          // If the "world" is bigger than the screen this is the players
                                                        // position in relation to the world / map
    public int speed;                                   // Stores the entities movement speed

    // Graphics and Animation Stuff
    // TODO: SPRITE SHEETS
    //Stores 10 necessary images to create animated movement for an entity
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, ideal1, ideal2;

    public String direction;                            // Stores information on the current direction an entity is facing
    public int spriteCounter;                           // Used to determine when the next image should be drawn
    public int spriteNumber = 1;
    //public int spriteIndex;                             // The index of the sprite to draw within the sprite-sheet

    // Collision Stuff
    public AABB entitiesCollisionBox;                   // The box that will be used to determine collisions
}