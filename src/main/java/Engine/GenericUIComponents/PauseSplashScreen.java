package Engine.GenericUIComponents;
import Engine.GameWindow;
import Engine.States.STATES;
import java.awt.*;
import java.util.ArrayList;

public class PauseSplashScreen {
    private final GameWindow gameWindow;

    // The buttons that will populate the pause screen
    private final UIButton resumeButton;
    private final UIButton optionsButton;
    private final UIButton exitToMainMenuButton;
    private final UIButton exitToDesktopButton;

    // I want the buttons to be the same width and height so will add them all to a list
    // and use my collection of buttons class to arrange them to all be the same size
    private ArrayList<UIButton> collectionOfButtons;

    public PauseSplashScreen(GameWindow gw) {
        this.gameWindow = gw;
        Font font = new Font("Default", Font.BOLD, 22);

        // Getting the centre point
        int x = gameWindow.getHalfScreenWidth();
        int y = gameWindow.getHalfScreenHeight();

        int padding = 12;
        int spacing = 20;

        // Creating the buttons
        resumeButton = new UIButton(x, y, "Resume ", font, padding, this::resumeGameplay);
        optionsButton = new UIButton(x, (y + resumeButton.getHeight()) + spacing, "Options", font, padding, this::openOptionMenu);
        exitToMainMenuButton = new UIButton(x, (optionsButton.getY() + optionsButton.getHeight()) + spacing, "Exit to Main Menu", font, padding, this::exitToMainMenu);
        exitToDesktopButton = new UIButton(x, (exitToMainMenuButton.getY() + exitToMainMenuButton.getHeight()) + spacing, "Exit to Desktop", font, padding, this::exitToDesktop);

        // Resizing the buttons to be the same size
        resizeButtons();
    }

    private void resizeButtons() {
        collectionOfButtons = new ArrayList<>();
        collectionOfButtons.add(resumeButton);
        collectionOfButtons.add(optionsButton);
        collectionOfButtons.add(exitToMainMenuButton);
        collectionOfButtons.add(exitToDesktopButton);

        CollectionOfUIButtons collectionOfUIButtons = new CollectionOfUIButtons(collectionOfButtons);
        collectionOfButtons = collectionOfUIButtons.getResizedButtons();
    }

    public void draw(Graphics2D graphics2D) {
        for(UIButton button : collectionOfButtons) {
            button.draw(graphics2D);
        }
    }

    // Button methods
    private void resumeGameplay() {
        gameWindow.getGameStateManager().queueStateSwitchPauseAndPlay(STATES.PLAY, false);
    }

    private void openOptionMenu() {
        System.out.println("Option menu was opened");
        System.exit(0);
    }

    private void exitToMainMenu() {
        getGameWindow().resetWorld();
        getGameWindow().getGameStateManager().queueStateSwitchPauseAndPlay(STATES.MAIN_MENU, true);
    }

    private void exitToDesktop() { System.exit(0); }

    // Button Getters
    public GameWindow getGameWindow() {
        return gameWindow;
    }
    public UIButton getResumeButton() {
        return resumeButton;
    }
    public UIButton getOptionsButton() {
        return optionsButton;
    }
    public UIButton getExitToMainMenuButton() {
        return exitToMainMenuButton;
    }
    public UIButton getExitToDesktopButton() {
        return exitToDesktopButton;
    }
}