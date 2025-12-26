import Engine.GameWindow;
import javax.swing.JFrame;
// GameContext could properly replace lots of calls to getters and other dependencies being injected
// GameContext is injected into entities this is unnecessary look at start using lightweight interfaces

// GameWindow global somehow?

// TODO: Should change the x,y for entities to be floats and cast to Int when we draw just for smoother movement!
// TODO: So many hardcoded values that need to be gone!
// TODO: Entities chasing player need variation of speed and positioning (don't all collapse on top of each other)
// TODO: Fix the "attacking" of a player (this has semi been improvement, now really need some kind of attack animation and cooldown)
// TODO: In some instances the enemy chasing the player can pass through collide-able objects

// TODO: WHEN YOU COME BACK DO THE FOLLOWING:
// Look at only allowing player to damage an enemy when within a certain radius
// Attack animation??? Potential day task

/** -------------------------------------------------------------------------------------------------------------------
 *                          CURRENT TASK
 *  - Rewrite the implementation of clickable
 *  I like the way we can only click on entities that implement clickable, but I do not like the override method.
 *  At the moment if I want to "attack" an enemy I need to override in the enemy, I do not like this approach
 *  The player can also hit itself in theory then...
 *  I think what needs to happen is the player needs to know what it can hit (a clickable entity) but it should only do so
 *  IF within a certain attack range, can hit the entity, i.e., an enemy maybe want logic for clicking on NPC(s) who knows
 */
public class EntryPoint {
    public static void main(String[] args) {
        // Create a frame to hold the panel
        JFrame window = new JFrame("CoffeeBeans");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setUndecorated(true);

        // Create the custom panel and add it to the frame
        GameWindow gameWindow = new GameWindow();
        window.add(gameWindow);

        //Making the window the size of the desired game window
        window.pack();

        //Setting the window to the centre of the users screen
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}