package Game;
import Engine.Entity.Entity;
import Engine.Event.Event;

// This doesn't really do anything, it's more of a way of triggering set events / lets the EventBus know what to do.
// I.e., kinda type safe event handling, if a player dies play a sound but if we passed in a different event we do something else
// The actual logic is handled by registering an event
public class EnemyDiedEvent implements Event {
    public EnemyDiedEvent() {
    }
}