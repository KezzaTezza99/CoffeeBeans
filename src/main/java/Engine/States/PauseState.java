package Engine.States;
import Engine.Input.KeyHandler;
import Engine.Managers.GameStateManager;
import java.awt.*;

public class PauseState extends GameState {
    private GameStateManager gameStateManager;

    public PauseState(GameStateManager gm, boolean isActive) {
        super(gm, isActive);
        this.gameStateManager = gm;
    }

    @Override
    public void update() {

    }

    @Override
    // TODO: WHY CANT WE USE THE P KEY FOR PAUSING AND UNPAUSING? IT DOES BOTH AT SAME TIME
    public void input(KeyHandler keyHandler) {
//        if(keyHandler.pause.clicked && gameStateManager.getCurrentState() == STATES.PAUSE && gameStateManager.pauseFlag) {
//            System.out.println("WE HAVE UN-PAUSED THE GAME");
//            gameStateManager.setGameStateIsActive(STATES.PLAY, true);
//            gameStateManager.setGameStateIsActive(STATES.PAUSE, false);
//            gameStateManager.setCurrentState(STATES.PLAY);
//            gameStateManager.setPauseFlag(false);
//        }

        if (keyHandler.pause.clicked && gameStateManager.getCurrentState() == STATES.PAUSE && gameStateManager.pauseFlag) {
            System.out.println("WE HAVE UN-PAUSED THE GAME");
            gameStateManager.queueStateSwitch(STATES.PLAY, false);
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.red);
        graphics2D.drawRect(100, 100, 200, 200);
    }
}