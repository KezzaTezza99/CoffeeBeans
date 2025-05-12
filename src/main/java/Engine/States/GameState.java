package Engine.States;

import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.GameStateManager;

import java.awt.*;

// TODO: STATE NEEDS WINDOW SO WE CAN CLEAR THE BUFFER TO DISPLAY NEW STUFF I.E., PAUSE MENU, DEATH ETC...
public abstract class GameState {
    private GameStateManager gameStateManager;
    private boolean isActive;
    private boolean blockUpdate = false;
    private boolean overlay = false;

    public GameState(GameStateManager gm, boolean isActive) {
        this.gameStateManager = gm;
        this.isActive = isActive;
    }

    public abstract void update();

    // TODO: handle the input, cause if I use just input(key) it doesn't work
    public abstract void input(KeyHandler keyHandler);
    public abstract void input(KeyHandler keyHandler, MouseHandler mouseHandler);
    public abstract void draw(Graphics2D graphics2D);

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) { isActive = active; }
    public boolean isBlockUpdate() {
        return blockUpdate;
    }
    public void setBlockUpdate(boolean blockUpdate) {
        this.blockUpdate = blockUpdate;
    }
    public boolean isOverlay() {
        return overlay;
    }
    public void setOverlay(boolean overlay) {
        this.overlay = overlay;
    }
}