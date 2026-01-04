package Engine.Collisions;
import java.awt.*;

public class AABB {
    private float x, y;                             // Position of the bottom left corner
    private final float width, height;              // Dimensions of the collision box

    public AABB(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isCollidingWith(AABB other) {
        return this.x <= other.x + other.width &&
                this.x + this.width >= other.x &&
                this.y <= other.y + other.height &&
                this.y + this.height >= other.y;
    }

    public boolean mouseIsCollidingWith(int x, int y) {
        return x >= this.x &&
                x <= this.x + width &&
                y >= this.y &&
                y <= this.y + height;
    }

    // For debugging purposes
    public void drawCollider(Graphics2D graphics2D, Color colour) {
        graphics2D.setColor(colour);
        graphics2D.drawRect((int) this.getX(), (int) this.getY(), (int) this.getWidth(), (int) this.getHeight());
    }

    // Getters
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }

    // Setters
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
}