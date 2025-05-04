package Engine.Dispatcher;

public interface EventListener<T extends Event> {
    void onEvent(T event);
}
