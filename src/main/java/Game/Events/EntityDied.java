package Game.Events;

import Engine.Dispatcher.Event;
import Engine.Entity.Entity;

public record EntityDied(Entity entity) implements Event { }
