import Engine.GameWindow;
import javax.swing.JFrame;

// TODO: So many hardcoded values that need to be gone!
// TODO: Entities chasing player need variation of speed and positioning (don't all collapse on top of each other)
// TODO: Fix the "attacking" of a player (this has semi been improvement, now really need some kind of attack animation and cooldown)
// TODO: In some instances the enemy chasing the player can pass through collide-able objects

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