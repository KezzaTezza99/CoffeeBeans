import Engine.GameWindow;
import javax.swing.JFrame;

// TODO: I am loading the data completely wrong, this needs fixing ASAP

/*
    In summary:
    Rows → y-axis (vertical)
    Columns → x-axis (horizontal)
    Thus, when you access array[row][column], you are accessing the element at the (x, y) position in the grid.
 */

public class EntryPoint {
    public static void main(String[] args) {
        // Create a frame to hold the panel
        JFrame window = new JFrame("CoffeeBeans");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        //window.setUndecorated(true);    // borderless fullscreen

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