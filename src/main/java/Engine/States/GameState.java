package Engine.States;

import Engine.Input.KeyHandler;
import Engine.Managers.GameStateManager;

import java.awt.*;

// TODO: Need to have a way of only drawing active state
// TODO: Also need a way to check if a key was just pressed, I think? We can't "pause" because it calls both things when
// escape is pressed
// maybe a paused boolean?

// TODO: STATE NEEDS WINDOW SO WE CAN CLEAR THE BUFFER TO DISPLAY NEW STUFF I.E., PAUSE MENU, DEATH ETC...
public abstract class GameState {
    private GameStateManager gameStateManager;
    private boolean isActive;

    public GameState(GameStateManager gm, boolean isActive) {
        this.gameStateManager = gm;
        this.isActive = isActive;
    }

    public abstract void update();
    public abstract void input(KeyHandler keyHandler);
    public abstract void draw(Graphics2D graphics2D);

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) { isActive = active; }
}