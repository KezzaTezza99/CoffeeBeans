package Game.UIElements;

import java.awt.*;

// TEMP COLOURS AND STUFF WOULD CHANGE THIS
// PROBABLY HAVE A WAY TO SET THE COLORS IF DON'T WANT DEFAULT!
public class StatBar {
    private int x, y, width, height;
    private Color backgroundColor = Color.darkGray;
    private Color fillColor = Color.green;
    private Color borderColor = Color.black;

    private int maxValue;
    private int currentValue;

    public StatBar(int x, int y, int w, int h, int mv) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.maxValue = mv;
        this.currentValue = mv;
    }

    public void setCurrentValue(int value) {
        this.currentValue = Math.max(0, Math.min(value, maxValue));
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(backgroundColor);
        graphics2D.fillRect(x, y, width, height);

        // Fill the bar based on percentage
        int fillWidth = (int)((currentValue / (double) maxValue) * width);
        graphics2D.setColor(fillColor);
        graphics2D.fillRect(x, y, fillWidth, height);

        // Border of the bar
        graphics2D.setColor(borderColor);
        graphics2D.drawRect(x, y, width, height);
    }
}
