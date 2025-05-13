import Engine.GameWindow;
import javax.swing.JFrame;

// TODO: A way to test movements passed the screen, i.e. load a new map or have a "world" that is bigger than the screen

// TODO: 04/05/25 -> We need to start thinking about how often we pass stuff around, do we use dependency injection or the idea of
// EngineContext which has all the necessary core services, i.e., EventBus, SoundManager and pass EngineContext when needed?

// TODO: Look into expanding the EventBus by looking at the following:
// EVENT PRIORITIES, ONE-SHOT LISTENERS, EVENT BUBBLING

// TODO: 05/05/25 -> Clickable entities for games that would use point and click for attacking (potentially movement too)
// Could test if clicking would trigger AABB for successful sword attack for example?

// Do we want to clean up before we exit the application?

// Should we inject important information about the GameWindow and stuff into a service provider, i.e. window info?

// GameContext could properly replace lots of calls to getters and other dependencies being injected

// GameContext is enjected into entities this is unnecessary look at start using lightweight interfaces
// URGENT:
// Implementation for next sprint implement hopefully by 12.05.25
//----------------------------------------------------------------------------------------------------------------------
// Gameplay Features:
// Death splash screen appears when the player dies have some info then after an N amount of time we fade back to the
// main menu. Think I'll need some UI components creating and a timer class creating to implement everything we need

// UI Components:
// Create a generic dialog system, it produces something visual on screen with some text (simulate interacting with an NPC)
// Create a method in UIManager that prints dialog / string to the screen with an x,y and label parameter
//----------------------------------------------------------------------------------------------------------------------
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