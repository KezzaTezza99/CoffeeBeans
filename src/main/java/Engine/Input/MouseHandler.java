package Engine.Input;
import Engine.GameWindow;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    private int mouseX, mouseY;
    private boolean clicked;

    public MouseHandler(GameWindow gameWindow) {
        gameWindow.addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        clicked = true;
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public boolean isClicked() {
        return clicked;
    }
    public void resetClick() {
        clicked = false;
    }
    public int getMouseX() {
        return mouseX;
    }
    public int getMouseY() {
        return mouseY;
    }
}