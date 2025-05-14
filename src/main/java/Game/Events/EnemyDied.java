package Game.Events;
import Engine.Dispatcher.Event;
import Engine.Entity.Enemy;

public class EnemyDied implements Event {
    public EnemyDied(Enemy enemy) {
        enemy.setIsAlive(false);
    }
}