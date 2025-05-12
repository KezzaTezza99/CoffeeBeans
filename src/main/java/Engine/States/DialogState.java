package Engine.States;
import Engine.Entity.Entity;
import Engine.Entity.EntityType;
import Engine.Entity.NPC;
import Engine.GenericUIComponents.DialogOverlay;
import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.GameStateManager;
import java.awt.*;
import java.util.List;

public class DialogState extends GameState {
    private final DialogOverlay dialogOverlay;
    private final GameStateManager gameStateManager;

    public DialogState(GameStateManager gm, boolean isActive) {
        super(gm, isActive);
        setOverlay(true);
        this.gameStateManager = gm;
        dialogOverlay = new DialogOverlay(gm.getGameWindow(), "This is a message");
    }

    @Override
    public void update() {

    }

    @Override
    public void input(KeyHandler keyHandler) {}

    @Override
    public void input(KeyHandler keyHandler, MouseHandler mouseHandler) {
        if(keyHandler.enter.clicked) {
            List<Entity> npcs = gameStateManager.getGameContext().getEntityManager().getEntityByTag(EntityType.NPC);

            if(!npcs.isEmpty()) {
                NPC npc = (NPC) npcs.get(0);
                npc.hasTriggered = true;
                gameStateManager.setGameStateIsActive(STATES.DIALOG, false);
                gameStateManager.blockInputForSelectedState(STATES.PLAY, false);
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        dialogOverlay.draw(graphics2D);
    }
}