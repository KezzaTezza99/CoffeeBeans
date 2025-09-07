package Engine.States;
import Engine.GenericUIComponents.UIButton;
import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.GameStateManager;
import java.awt.*;

public class GameOverState extends GameState {
    private final GameStateManager gameStateManager;

    // CAN WE JUST HAVE A REFERENCE TO THE SPLASH-SCREEN UI???

    public GameOverState(GameStateManager gm, boolean isActive) {
        super(gm, isActive);
        this.gameStateManager = gm;
        setBlockUpdate(false);

        // We could initialise the splash-screen here, can then just swap out for whatever we need?
    }

    @Override
    public void update() {}
    @Override
    public void input(KeyHandler keyHandler) {}

    @Override
    public void input(KeyHandler keyHandler, MouseHandler mouseHandler) {
        // If we have a reference to the splash-screen we will need to grab the buttons and then handle any clicks
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        // Just testing the state works
        graphics2D.setColor(Color.YELLOW);
    }
}