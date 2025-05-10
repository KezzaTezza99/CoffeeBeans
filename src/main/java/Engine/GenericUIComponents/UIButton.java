package Engine.GenericUIComponents;

import java.awt.*;

public class UIButton {
    private int x, y, width, height;
    private String label;
    private Runnable onClick;

    public UIButton(int x, int y, int w, int h, String l, Runnable onClick) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.label = l;
        this.onClick = onClick;
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.LIGHT_GRAY);
        graphics2D.fillRect(x, y, width, height);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect(x, y, width, height);
        graphics2D.drawString(label, x + 10, y + height / 2);
    }

    public void handleClick(int mouseX, int mouseY) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            onClick.run();
        }
    }
}
