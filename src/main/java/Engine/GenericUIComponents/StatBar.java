package Engine.GenericUIComponents;

import java.awt.*;

public class StatBar {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private Color backgroundColor = Color.darkGray;
    private Color fillColor = Color.green;
    private Color borderColor = Color.black;

    private final int maxValue;
    private int currentValue;

    public StatBar(int x, int y, int width, int height, int maxValue) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxValue = maxValue;
        this.currentValue = maxValue;
    }

    public void setAllColours(Color backgroundColor, Color fillColor, Color borderColor) {
        this.backgroundColor = backgroundColor;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    public void setFillColor(Color fillColor) { this.fillColor = fillColor; }

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
