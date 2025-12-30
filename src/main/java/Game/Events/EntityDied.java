package Game.Events;

import Engine.Dispatcher.Event;
import Engine.Entity.Entity;

public class EntityDied implements Event {
    public final Entity entity;

    public EntityDied(Entity entity) {
        this.entity = entity;
    }
}
