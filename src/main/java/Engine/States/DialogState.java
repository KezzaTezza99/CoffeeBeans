package Engine.States;
import Engine.GenericUIComponents.DialogOverlay;
import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.GameStateManager;
import java.awt.*;

public class DialogState extends GameState {
    private final DialogOverlay dialogOverlay;

    public DialogState(GameStateManager gm, boolean isActive) {
        super(gm, isActive);
        setOverlay(true);
        dialogOverlay = new DialogOverlay(gm.getGameWindow(), "This is a message");
    }

    @Override
    public void update() {

    }

    @Override
    public void input(KeyHandler keyHandler) {

    }

    @Override
    public void input(KeyHandler keyHandler, MouseHandler mouseHandler) {

    }

    @Override
    public void draw(Graphics2D graphics2D) {
        dialogOverlay.draw(graphics2D);
    }
}