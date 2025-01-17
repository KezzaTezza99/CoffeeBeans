package Engine.Entity;

import Engine.Collisions.AABB;

public class Entity {
    // Generic Entity Stuff
    public int x,y;                                     // Stores the entities (x,y) position
    public int worldX, worldY;                          // If the "world" is bigger than the screen this is the players
                                                        // position in relation to the world / map
    public int speed;                                   // Stores the entities movement speed

    // Graphics and Animation Stuff
    // TODO: SPRITE SHEETS
    public String direction;                            // Stores information on the current direction an entity is facing
    public int spriteCounter;                           // Used to determine when the next image should be drawn
    public int spriteIndex;                             // The index of the sprite to draw within the sprite-sheet

    // Collision Stuff
    public AABB entitiesCollisionBox;                   // The box that will be used to determine collisions
}