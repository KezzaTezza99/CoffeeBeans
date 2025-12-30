package Game.Events;

import Engine.Dispatcher.Event;
import Engine.Entity.Entity;

public class DamageTaken implements Event {
    public final Entity entity;
    public final int damageTaken;
    private final int originalHealth;

    public DamageTaken(Entity self, int damageTaken) {
        this.entity = self;
        this.damageTaken = damageTaken;
        this.originalHealth = entity.getHealth();

        this.entity.setHealth(originalHealth - damageTaken);
    }

    public int getNewHealth() {
        return  (originalHealth - damageTaken);
    }
}