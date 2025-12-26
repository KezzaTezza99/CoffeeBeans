package Engine.Input;

import Engine.Services.EventBusService;
import Engine.Services.GameContextService;
import Game.Events.DrawHP;
import Game.Events.EnemyTookDamage;

public interface Clickable {
    int getDamage();

    default void onClick() {
        EventBusService.getBus().post(new DrawHP(getDamage(), getXMousePos(), getYMousePos()));
//        EventBusService.getBus().post(new EnemyTookDamage(this.health, this));
    }

    private int getXMousePos() { return GameContextService.get().getMouseHandler().getMouseX(); }
    private int getYMousePos() { return GameContextService.get().getMouseHandler().getMouseY(); }
}