package Engine.Managers;
import Engine.Input.KeyHandler;
import Engine.States.GameState;
import Engine.States.PauseState;
import Engine.States.PlayState;
import Engine.States.STATES;
import java.awt.*;
import java.util.HashMap;

// TODO: May be worth having some custom constructors here, i.e., give a list of states and initialise them all???

public class GameStateManager {
    private final HashMap<STATES, GameState> states = new HashMap<STATES, GameState>();
    private STATES currentState;

    // Default Constructor
    public GameStateManager() {
        states.put(STATES.PLAY, new PlayState(this, true));
        states.put(STATES.PAUSE, new PauseState(this, false));

        currentState = STATES.PLAY;
    }

    public void update() {
        for (GameState state : states.values()) {
            state.update();
        }
    }

    public void input(KeyHandler key) {
        for (GameState state: states.values()) {
            state.input(key);
        }
    }

    public void draw(Graphics2D graphics2D) {
        for (GameState state: states.values()) {
            if(state.isActive()) {
                state.draw(graphics2D);
            }
        }
    }

    // Useful Getters and Setters
    public STATES getCurrentState() {
        return currentState;
    }
    public void setCurrentState(STATES currentState) {
        this.currentState = currentState;
    }
    public void setGameStateIsActive(STATES stateToUpdate, boolean isActive) {
        states.get(stateToUpdate).setActive(isActive);
    }
}