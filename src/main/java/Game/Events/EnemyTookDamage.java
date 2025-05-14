package Game.Events;
import Engine.Dispatcher.Event;
import Engine.Entity.Enemy;
import Engine.Services.EventBusService;

public class EnemyTookDamage implements Event {
    private final Enemy enemy;

    public EnemyTookDamage(int newHealth, Enemy enemy) {
        this.enemy = enemy;

        EventBusService.getBus().register(EnemyDied.class, event -> this.enemy.enemyDied());

        if(newHealth == 0 || newHealth < 0) {
            EventBusService.getBus().post(new EnemyDied(this.enemy));
        }
    }
}
