package Engine.Managers;

import Engine.Collisions.AABB;
import Engine.GameWindow;
import Engine.States.STATES;

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

    // TODO:
    // Edge Case Safety: might want to clamp col and row in willCollide to make sure they don’t go out of bounds when near map edges
    public boolean willCollide(AABB futureBounds) {
        int leftTile = (int)(futureBounds.getX()) / gameWindow.getTileSize();
        int rightTile = (int)(futureBounds.getX() + futureBounds.getWidth() - 1) / gameWindow.getTileSize();
        int topTile = (int)(futureBounds.getY()) / gameWindow.getTileSize();
        int bottomTile = (int)(futureBounds.getY() + futureBounds.getHeight() - 1) / gameWindow.getTileSize();

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
}
