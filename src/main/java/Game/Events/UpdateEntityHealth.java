package Game.Events;

import Engine.Dispatcher.Event;
import Engine.Entity.Entity;

public class UpdateEntityHealth implements Event {
    public UpdateEntityHealth(Entity entity, int newHealth) {
        entity.setHealth(newHealth);
    }
}