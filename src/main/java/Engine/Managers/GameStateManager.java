package Engine.Managers;
import Engine.Entity.Enemy;
import Engine.Entity.EntityType;
import Engine.GameContext;
import Engine.GameWindow;
import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Services.EventBusService;
import Engine.Services.GameContextService;
import Engine.States.*;
import Game.Events.EntityDied;

import java.awt.*;
import java.util.HashMap;

// TODO: May be worth having some custom constructors here, i.e., give a list of states and initialise them all???

public class GameStateManager {
    public final HashMap<STATES, GameState> states = new HashMap<>();
    private STATES currentState;
    public boolean pauseFlag = false;
    private STATES queuedState = null;

    // TODO: Realistically is the stateToInit even needed? So far we only use if to initialise the main menu
    // When we die etc., we reinit and just recreate everything, realistically it works for now but we should do this
    // smarter in the future!

    // Default Constructor
    public GameStateManager(STATES stateToInit) {
        EventBusService.getBus().register(EntityDied.class, event -> {
            event.entity().setIsAlive(false);
            if(event.entity().getTag() == EntityType.PLAYER) {
                gameOver();
            }

            if(event.entity().getTag() == EntityType.ENEMY) {
                Enemy.enemyDied();
            }
        });
        init(stateToInit);
    }

    private void init(STATES currentState) {
        states.put(STATES.MAIN_MENU, new MainMenuState(this, true));
        states.put(STATES.PLAY, new PlayState(this, false));
        states.put(STATES.PAUSE, new PauseState(this, false));
        states.put(STATES.DIALOG, new DialogState(this, false));
        states.put(STATES.GAME_OVER, new GameOverState(this, false));

        this.currentState = currentState;
    }

    public void update() {
        if (queuedState != null) {
            setGameStateIsActive(currentState, false);
            setGameStateIsActive(queuedState, true);
            setCurrentState(queuedState);
            queuedState = null;
            return; // Skip update this frame to avoid weirdness
        }

        for (GameState state : states.values()) {
            if (state.isActive()) {
                state.update();
            }
        }
    }

    public void input(KeyHandler key) {
        for (GameState state: states.values()) {
            if(state.isActive() && !state.isBlockUpdate()) {
                state.input(key);
            }
        }
    }

    public void input(KeyHandler key, MouseHandler mouse) {
        for (GameState state: states.values()) {
            if(state.isActive() && !state.isBlockUpdate()) {
                state.input(key, mouse);
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        for (GameState state : states.values()) {
            if (state.isActive() && !state.isOverlay()) {
                state.draw(graphics2D);
            }
        }

        for (GameState state : states.values()) {
            if (state.isActive() && state.isOverlay()) {
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

    // TODO: rename these variables, it's confusing when we come back to the code what this is doing
    // it basically decided the next state and if we should allow for the state underneath to keep running etc
    // i.e. if the game should keep ticking etc
    public void queueStateSwitchPauseAndPlay(STATES nextState, boolean pauseFlag) {
        this.queuedState = nextState;
        this.pauseFlag = pauseFlag;
    }

    public void blockInputForCurrentState(boolean flag) {
        GameState state = states.get(currentState);
        state.setBlockUpdate(flag);
    }

    public void blockInputForSelectedState(STATES state, boolean flag) {
        GameState stateToUpdate = states.get(state);
        stateToUpdate.setBlockUpdate(flag);
    }

    private void gameOver() {
        queueStateSwitchPauseAndPlay(STATES.GAME_OVER, true);
        GameContextService.get().getSoundManager().closeAll(true);
    }
}