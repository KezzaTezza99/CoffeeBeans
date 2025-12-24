import Engine.GameWindow;
import javax.swing.JFrame;
// GameContext could properly replace lots of calls to getters and other dependencies being injected
// GameContext is injected into entities this is unnecessary look at start using lightweight interfaces

// GameWindow global somehow?

// TODO: Should change the x,y for entities to be floats and cast to Int when we draw just for smoother movement!
// TODO: So many hardcoded values that need to be gone!
// TODO: Entities chasing player need variation of speed and positioning (don't all collapse ontop of each other)
// TODO: Fix the "attacking" of a player
// TODO: In some instances the enemy chasing the player can pass through collide-able objects

// TODO: WHEN YOU COME BACK DO THE FOLLOWING:
// Look at only allowing player to damage an enemy when within a certain radius
// Attack animation??? Potential day task

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