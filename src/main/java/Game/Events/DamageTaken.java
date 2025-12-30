package Game.Events;

import Engine.Dispatcher.Event;
import Engine.Entity.Entity;
import Engine.Services.EventBusService;

public class DamageTaken implements Event {
    public final Entity entity;
    public final int damageTaken;
    private final int originalHealth;

    public DamageTaken(Entity entity, int damageTaken) {
        this.entity = entity;
        this.damageTaken = damageTaken;
        this.originalHealth = entity.getHealth();
        int newHealth = (originalHealth - damageTaken);

        EventBusService.getBus().post(new UpdateEntityHealth(entity, newHealth));
    }

    public int getNewHealth() {
        return  (originalHealth - damageTaken);
    }
}