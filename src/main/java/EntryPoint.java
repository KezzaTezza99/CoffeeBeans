import Engine.GameWindow;
import javax.swing.JFrame;

// TODO: A way to test movements passed the screen, i.e. load a new map or have a "world" that is bigger than the screen

// TODO: 04/05/25 -> We need to start thinking about how often we pass stuff around, do we use dependency injection or the idea of
// EngineContext which has all the necessary core services, i.e., EventBus, SoundManager and pass EngineContext when needed?

// TODO: Look into expanding the EventBus by looking at the following:
// EVENT PRIORITIES, ONE-SHOT LISTENERS, EVENT BUBBLING

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