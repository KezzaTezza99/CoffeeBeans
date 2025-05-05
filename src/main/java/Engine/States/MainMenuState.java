package Engine.States;
import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.GameStateManager;
import Game.UIElements.GenericUIButton;
import java.awt.*;

public class MainMenuState extends GameState {
    private final GameStateManager gameStateManager;
    private GenericUIButton button;

    public MainMenuState(GameStateManager gm, boolean isActive) {
        super(gm, isActive);
        this.gameStateManager = gm;

        button = new GenericUIButton(
                gameStateManager.getGameWindow().getHalfScreenWidth(),
                gameStateManager.getGameWindow().getHalfScreenHeight() + (gameStateManager.getGameWindow().getTileSize() / 2),
                100,
                50,
                "Play",
                this::loadGame);
    }

    @Override
    public void update() {}
    @Override
    public void input(KeyHandler keyHandler) {
    }

    @Override
    public void input(KeyHandler keyHandler, MouseHandler mouseHandler) {
        if(keyHandler.escape.clicked) System.exit(0);

        if(mouseHandler.isClicked()) {
            button.handleClick(mouseHandler.getMouseX(), mouseHandler.getMouseY());
            mouseHandler.resetClick();
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.white);
        graphics2D.drawString("Main Menu", gameStateManager.getGameWindow().getHalfScreenWidth(), gameStateManager.getGameWindow().getHalfScreenHeight());
        button.draw(graphics2D);
    }

    private void loadGame() {
        gameStateManager.setGameStateIsActive(STATES.MAIN_MENU, false);
        gameStateManager.setGameStateIsActive(STATES.PLAY, true);
        gameStateManager.setCurrentState(STATES.PLAY);
    }
}