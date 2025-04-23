package Engine.Managers;
import Engine.Entity.Entity;
import Engine.GameWindow;
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
    }

    public void draw(Graphics2D graphics2D) {
        for(Entity entity : entities) {
            entity.draw(graphics2D);
        }
    }
}