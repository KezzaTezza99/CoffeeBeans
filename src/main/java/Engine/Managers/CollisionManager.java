package Engine.Managers;

import Engine.Collisions.AABB;
import Engine.Entity.Entity;
import Engine.GameWindow;
import Engine.Services.GameContextService;
import Engine.Utility.GameConstants;

import java.awt.*;

public class CollisionManager {

    public boolean checkTile(int x, int y) {
        int col = x / GameConstants.TILE_SIZE;
        int row = y / GameConstants.TILE_SIZE;

        int tile = GameContextService.get().getTileManager().mapSpriteData[col][row];

        if(GameContextService.get().getTileManager().sprites[tile].isCollidable) {
            System.out.println("We have hit a wall");
            return false;
        }
        return true;
    }

    public boolean willCollide(AABB futureBounds) {
        int leftTile = (int)(futureBounds.getX()) / GameConstants.TILE_SIZE;
        int rightTile = (int)(futureBounds.getX() + futureBounds.getWidth() - 1) / GameConstants.TILE_SIZE;
        int topTile = (int)(futureBounds.getY()) / GameConstants.TILE_SIZE;
        int bottomTile = (int)(futureBounds.getY() + futureBounds.getHeight() - 1) / GameConstants.TILE_SIZE;

        // Optional bounds check if we allow moving off-screen / camera follow
        if (GameContextService.get().getTileManager().canMoveOffScreen) {
            if (leftTile < 0 || rightTile >= GameContextService.get().getTileManager().mapSpriteData.length ||
                    topTile < 0 || bottomTile >= GameContextService.get().getTileManager().mapSpriteData[0].length) {
                return false;
            }
        }

        for (int col = leftTile; col <= rightTile; col++) {
            for (int row = topTile; row <= bottomTile; row++) {
                int tile = GameContextService.get().getTileManager().mapSpriteData[col][row];
                if (GameContextService.get().getTileManager().sprites[tile].isCollidable) {
                    return true;
                }
            }
        }
        return false;
    }

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

    // Just some easy methods to call so we aren't chaining methods a lot
    public boolean withinDamageRangeAndMouseIsIntersecting(Entity entity, AABB self, AABB other) {
        return entity.isMouseOver() && isCollidingWithTrigger(self, other);
    }
}