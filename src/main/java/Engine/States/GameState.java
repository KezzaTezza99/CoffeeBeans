package Engine.States;

import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.GameStateManager;

import java.awt.*;

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
    public abstract void input(KeyHandler keyHandler, MouseHandler mouseHandler);
    public abstract void draw(Graphics2D graphics2D);

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) { isActive = active; }
}