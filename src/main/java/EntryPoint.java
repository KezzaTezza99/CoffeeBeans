import Engine.GameWindow;
import javax.swing.JFrame;
// GameContext could properly replace lots of calls to getters and other dependencies being injected
// GameContext is injected into entities this is unnecessary look at start using lightweight interfaces

// GameWindow global somehow?
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