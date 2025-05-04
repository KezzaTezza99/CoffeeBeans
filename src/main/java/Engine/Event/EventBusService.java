package Engine.Event;

// TODO: THIS IS A TEMP SOLUTION, USING AS A SINGLETON FOR TESTING PURPOSES
// We will come up with a better solution

public class EventBusService {
    private static final EventBus eventBus = new EventBus();

    public static EventBus getBus() {
        return eventBus;
    }
}