package Engine.Managers;

import Engine.Collisions.AABB;
import Engine.Entity.Entity;
import Engine.GameWindow;
import Engine.States.STATES;

import java.awt.*;

public class CollisionManager {
    TileManager tileManager;
    GameWindow gameWindow;

    public CollisionManager(TileManager tileManager, GameWindow gameWindow) {
        this.tileManager = tileManager;
        this.gameWindow = gameWindow;
    }

    public boolean checkTile(int x, int y) {
        int col = x / gameWindow.getTileSize();
        int row = y / gameWindow.getTileSize();

        int tile = tileManager.mapSpriteData[col][row];

        if(tileManager.sprites[tile].isCollidable) {
            System.out.println("We have hit a wall");
            return false;
        }
        return true;
    }

    public boolean willCollide(AABB futureBounds) {
        int leftTile = (int)(futureBounds.getX()) / gameWindow.getTileSize();
        int rightTile = (int)(futureBounds.getX() + futureBounds.getWidth() - 1) / gameWindow.getTileSize();
        int topTile = (int)(futureBounds.getY()) / gameWindow.getTileSize();
        int bottomTile = (int)(futureBounds.getY() + futureBounds.getHeight() - 1) / gameWindow.getTileSize();

        // Optional bounds check if we allow moving off-screen / camera follow
        if (tileManager.canMoveOffScreen) {
            if (leftTile < 0 || rightTile >= tileManager.mapSpriteData.length ||
                    topTile < 0 || bottomTile >= tileManager.mapSpriteData[0].length) {
                return false;
            }
        }

        for (int col = leftTile; col <= rightTile; col++) {
            for (int row = topTile; row <= bottomTile; row++) {
                int tile = tileManager.mapSpriteData[col][row];
                if (tileManager.sprites[tile].isCollidable) {
                    return true;
                }
            }
        }
        return false;
    }

    // TODO: DO WE NEED THIS WHY CANT WE JUST USE isCollidingWith?
//    public boolean isCollidingWithAnotherEntity(Entity self, Entity other) {
//        return self.getBounds().isCollidingWith(other.getBounds());
//    }
//
    public boolean isCollidingWithTrigger(AABB self, AABB trigger) {
        return self.isCollidingWith(trigger);
    }

    public boolean pointIntersectsAABB(Point point, AABB aabb) {
        float px = point.x;
        float py = point.y;

        return px >= aabb.getX() &&
                px <= aabb.getX() + aabb.getWidth() &&
                py >= aabb.getY() &&
                py <= aabb.getY() + aabb.getHeight();
    }

}