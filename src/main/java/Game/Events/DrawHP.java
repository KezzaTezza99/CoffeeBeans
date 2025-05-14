package Game.Events;
import Engine.Dispatcher.Event;

public class DrawHP implements Event {
    private final int hp;
    private final int x, y;

    public DrawHP(int hp, int xPos, int yPos) {
        this.hp = hp;
        this.x = xPos;
        this.y = yPos;
    }

    public int getHp() { return this.hp; }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}