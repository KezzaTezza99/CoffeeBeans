package Game.Events;
import Engine.Dispatcher.Event;

public class PlayerTookDamage implements Event {
    private final int newHealth;

    public PlayerTookDamage(int newHealth) {
        this.newHealth = newHealth;
    }

    public int getNewHealth() { return this.newHealth; }
}