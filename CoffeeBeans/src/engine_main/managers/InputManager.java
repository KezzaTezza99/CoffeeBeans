package engine_main.managers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed , isKeyPressed;

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        isKeyPressed = true;

        //Handling key presses
        if(keyCode == KeyEvent.VK_W) {
            upPressed = true;
        }

        if(keyCode == KeyEvent.VK_S) {
            downPressed = true;
        }

        if(keyCode == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if(keyCode == KeyEvent.VK_D) {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        isKeyPressed = false;

        //Handling key release
        if(keyCode == KeyEvent.VK_W) {
            upPressed = false;
        }

        if(keyCode == KeyEvent.VK_S) {
            downPressed = false;
        }

        if(keyCode == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if(keyCode == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
