package Engine.Input;
import Engine.GameWindow;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseHandler extends MouseAdapter implements MouseMotionListener {
    private int mouseX, mouseY;
    private boolean clicked;

    public MouseHandler(GameWindow gameWindow) {
        gameWindow.addMouseListener(this);
        gameWindow.addMouseMotionListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        clicked = true;
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
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