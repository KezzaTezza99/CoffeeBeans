package Game.Events;
import Engine.Dispatcher.Event;
import Engine.Services.EventBusService;
import Engine.Entity.Player;

public class PlayerTookDamage implements Event {
    private final int newHealth;
    private final Player player;

    public PlayerTookDamage(int newHealth, Player player) {
        this.player = player;
        this.newHealth = newHealth;

        EventBusService.getBus().register(PlayerDied.class, event -> this.player.playerDied());

        if(newHealth == 0 || newHealth < 0) {
            EventBusService.getBus().post(new PlayerDied(player.getGameWindow().getGameStateManager()));
        }
    }

    public int getNewHealth() { return this.newHealth; }
}