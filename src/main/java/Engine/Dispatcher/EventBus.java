package Engine.Dispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
    private final Map<Class<? extends Event>, List<EventListener<? extends Event>>> listeners = new HashMap<>();

    public <T extends Event> void register(Class<T> eventType, EventListener<T> listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> void post(T event) {
        List<EventListener<? extends Event>> eventListeners = listeners.get(event.getClass());

        if(eventListeners != null) {
            for(EventListener<? extends Event> listener : eventListeners) {
                ((EventListener<T>) listener).onEvent(event);
            }
        }

    }
}
