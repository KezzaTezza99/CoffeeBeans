package Engine.GenericUIComponents;

import Engine.Utility.TextPositionHelper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIButton {
    private final int x;
    private final int y;
    private int width;
    private int height;
    private final String label;
    private final Runnable onClick;

    public UIButton(int x, int y, int w, int h, String l, Runnable onClick) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.label = l;
        this.onClick = onClick;
    }

    // Create(s) a button that is the right size for the label with some predefined padding
    public UIButton(int x, int y, String label, Font font, int padding, Runnable onClick) {
        this.x = x;
        this.y = y;

        // Calculate the correct width and height needed
        BufferedImage temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = temp.createGraphics();

        graphics2D.setFont(font);
        FontMetrics fontMetrics = graphics2D.getFontMetrics();

        this.width = fontMetrics.stringWidth(label) + padding * 2;
        this.height = fontMetrics.getAscent() + fontMetrics.getDescent() + padding * 2;

        graphics2D.dispose();

        this.label = label;
        this.onClick = onClick;
    }

    public void draw(Graphics2D graphics2D) {
        // Default colors
        graphics2D.setColor(Color.LIGHT_GRAY);
        graphics2D.fillRect(x, y, width, height);  // Fill background with default color

        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect(x, y, width, height);  // Draw border with default color

        // Use TextPositionHelper to get the centered position for the label
        Point textPos = TextPositionHelper.getCentredTextPositionInsideUIElement(label, graphics2D.getFont(), x, y, width, height);

        // Set the text color
        graphics2D.setColor(Color.BLACK);  // Or use buttonTextColour if customizable

        // Draw the text at the calculated position
        graphics2D.drawString(label, textPos.x, textPos.y);
    }

    // Custom draw method for different colours
    public void draw(Graphics2D graphics2D, Color buttonBackgroundColour, Color buttonTextColour) {
       graphics2D.setColor(buttonBackgroundColour);
       graphics2D.fillRect(x, y, width, height);  // Fill background with custom color

       graphics2D.setColor(Color.BLACK);  // Border color (can also be customizable if needed)
       graphics2D.drawRect(x, y, width, height);  // Draw border

       // Use TextPositionHelper to get the centered position for the label
       Point textPos = TextPositionHelper.getCentredTextPositionInsideUIElement(label, graphics2D.getFont(), x, y, width, height);

       // Set the custom text color
       graphics2D.setColor(buttonTextColour);  // Custom text color

       // Draw the text at the calculated position
       graphics2D.drawString(label, textPos.x, textPos.y);
    }

    public void handleClick(int mouseX, int mouseY) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            onClick.run();
        }
    }

    // Getters and Setters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public String getLabel() { return label; }

    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
}
