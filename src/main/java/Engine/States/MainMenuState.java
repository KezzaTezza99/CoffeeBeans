package Engine.States;
import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.GameStateManager;
import Engine.GenericUIComponents.UIButton;
import Engine.Services.GameContextService;
import Engine.Utility.GameConstants;

import java.awt.*;

public class MainMenuState extends GameState {
    private final GameStateManager gameStateManager;
    private final UIButton playButton;
    private final UIButton exitButton;

    public MainMenuState(GameStateManager gm, boolean isActive) {
        super(gm, isActive);
        this.gameStateManager = gm;
        setBlockUpdate(false);

        playButton = new UIButton(
                GameConstants.HALF_SCREEN_WIDTH,
                GameConstants.HALF_SCREEN_HEIGHT + (GameConstants.TILE_SIZE / 2),
                100,
                50,
                "Play",
                this::loadGame);

        exitButton = new UIButton(
                GameConstants.HALF_SCREEN_WIDTH,
                (int) (GameConstants.HALF_SCREEN_HEIGHT + (GameConstants.TILE_SIZE / 0.5)),
                100,
                50,
                "Exit",
                this::exitGame);
    }

    @Override
    public void update() {}
    @Override
    public void input(KeyHandler keyHandler) {
    }

    @Override
    public void input(KeyHandler keyHandler, MouseHandler mouseHandler) {
        if(mouseHandler.isClicked()) {
            playButton.handleClick(mouseHandler.getMouseX(), mouseHandler.getMouseY());
            exitButton.handleClick(mouseHandler.getMouseX(), mouseHandler.getMouseY());
            mouseHandler.resetClick();
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.white);
        graphics2D.drawString(
                "Main Menu",
                GameConstants.HALF_SCREEN_WIDTH,
                GameConstants.HALF_SCREEN_HEIGHT);
        playButton.draw(graphics2D);
        exitButton.draw(graphics2D);
    }

    private void loadGame() {
        gameStateManager.setGameStateIsActive(STATES.MAIN_MENU, false);
        gameStateManager.setGameStateIsActive(STATES.PLAY, true);
        gameStateManager.setCurrentState(STATES.PLAY);

        // Game has been restarted reset the world
        if(GameContextService.get().getGameWindow().replayGame) GameContextService.get().getGameWindow().resetWorld();
    }

    // TODO: Eventually we could clean up memory, is it even worth it we could let garbage collection do it
    private void exitGame() { System.exit(0); }
}