package Game.Events;
import Engine.Dispatcher.Event;
import Engine.Services.EventBusService;

public class PlayerDied implements Event {
    public PlayerDied() {
        EventBusService.getBus().post(new DrawDeathSplashscreen());
    }
}