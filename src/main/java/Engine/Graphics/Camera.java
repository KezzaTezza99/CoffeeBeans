package Engine.Graphics;

import Engine.Entity.Entity;

// TODO: Finish this

public class Camera {
    public int x,y;                                     // Cameras position
    public int screenWidth, screenHeight;               // The width of the users screen and height
    public int worldWidth, worldHeight;                 // Some games will require a world bigger than the screen

    private Entity target;                              // The target the camera will follow
    private boolean lockToRoom = false;                 // Some games will require the camera to move around / not move around

    public Camera(int screenWidth, int screenHeight, boolean lockToRoom) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.worldWidth = screenWidth;
        this.worldHeight = screenHeight;
        this.lockToRoom = lockToRoom;
    }

    public Camera(int screenWidth, int screenHeight, int worldWidth, int worldHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public void update() {
        if(target == null) return;

        x = target.worldX - screenWidth / 2;
        y = target.worldY - screenHeight / 2;

        // Clamp the camera so it doesn't move past the worlds edges
        x = Math.max(0, Math.min(x, worldWidth - screenWidth));
        y = Math.max(0, Math.min(y, worldHeight - screenHeight));
    }
}