package Game.Events;
import Engine.Dispatcher.Event;
import Engine.Managers.GameStateManager;
import Engine.Managers.SoundManager;
import Engine.Services.EventBusService;
import Engine.States.STATES;

public class PlayerDied implements Event {
    private final GameStateManager gameStateManager;

    public PlayerDied(GameStateManager gsm) {
        this.gameStateManager = gsm;
        changeGameState();
    }

    public void changeGameState() {
        this.gameStateManager.queueStateSwitchPauseAndPlay(STATES.GAME_OVER, true);
        this.gameStateManager.getGameContext().getSoundManager().closeAll(true);
    }
}