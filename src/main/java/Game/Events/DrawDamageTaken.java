package Game.Events;
import Engine.Dispatcher.Event;

public class DrawDamageTaken implements Event {
    private final int x, y;

    public DrawDamageTaken(int xPos, int yPos) {
        this.x = xPos;
        this.y = yPos;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}