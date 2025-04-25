package Engine.Managers;
import Engine.Entity.Entity;
import Engine.Entity.EntityType;
import Engine.Entity.Enemy;
import Engine.Entity.Player;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    public List<Entity> entities;

    public EntityManager() {
        entities = new ArrayList<>();
    }

    public void addEntity(Entity entityToAdd) {
        entities.add(entityToAdd);
    }

    public void removeEntity(Entity entityToDelete) {
        entities.remove(entityToDelete);
    }

    public void update() {
        for(Entity entity : entities) {
            entity.update();
        }

        // Checking collisions
        for(int i = 0; i < entities.size(); i++) {
            for(int j = i + 1; j < entities.size(); j++) {
                Entity a = entities.get(i);
                Entity b = entities.get(j);

                // Check for direct collisions
                if(a.getBounds().isCollidingWith(b.getBounds())) {
                     a.handleCollision(b);
                     b.handleCollision(a);
                }

                // Check for vision radius collisions
                if(a.tag == EntityType.ENEMY && b.tag == EntityType.PLAYER) {
                    Enemy enemy = (Enemy) a;
                    Player player = (Player) b;


                }
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        for(Entity entity : entities) {
            entity.draw(graphics2D);
        }
    }
}