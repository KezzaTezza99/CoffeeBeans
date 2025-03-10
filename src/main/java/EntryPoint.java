import Engine.GameWindow;
import javax.swing.JFrame;

// TODO: I am loading the data completely wrong, this needs fixing ASAP

/*
    In summary:
    Rows → y-axis (vertical)
    Columns → x-axis (horizontal)
    Thus, when you access array[row][column], you are accessing the element at the (x, y) position in the grid.
 */

// TODO:
// Collision manager to track collisions
// Way to change an entities state, i.e., the entity has "died" or "despawned" etc.,
// A way to test movements passed the screen, i.e. load a new map or have a "world" that is bigger than the screen

// FIXES:
// Shouldn't be able to go off the screen (this is iffy, should be able to if the "world" is bigger than the screen)
// or potentially the game will move you to a new world? room? depends on the games needs so need to think about this!

public class EntryPoint {
    public static void main(String[] args) {
        // Create a frame to hold the panel
        JFrame window = new JFrame("CoffeeBeans");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        window.setUndecorated(true);    // borderless fullscreen

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