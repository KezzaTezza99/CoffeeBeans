package Game.Events;

import Engine.Dispatcher.Event;
import Engine.Entity.Entity;

public class SetEntityUpdatedHealth implements Event {
    public SetEntityUpdatedHealth(Entity entity, int newHealth) {
        entity.setHealth(newHealth);
    }
}