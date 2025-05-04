package Engine.Event;

public interface EventListener<T extends Event> {
    void onEvent(T event);
}
