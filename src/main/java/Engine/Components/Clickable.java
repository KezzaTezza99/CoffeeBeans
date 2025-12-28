package Engine.Components;

import Engine.Services.EventBusService;
import Engine.Services.GameContextService;
import Game.Events.DrawDamageTaken;

public interface Clickable {
    record MousePosition(int xPos, int yPos) {}

//    default MousePosition onClick() {
////        EventBusService.getBus().post(new DrawDamageTaken(getXMousePos(), getYMousePos()));
////        EventBusService.getBus().post(new EnemyTookDamage(this.health, this));
//        return new MousePosition(getXMousePos(), getYMousePos());
//    }

    default void onClick() {
        handleClickEvent();
    }

    default void handleClickEvent() {
        EventBusService.getBus().post(new DrawDamageTaken(getXMousePos(), getYMousePos()));
    }

    private int getXMousePos() { return GameContextService.get().getMouseHandler().getMouseX(); }
    private int getYMousePos() { return GameContextService.get().getMouseHandler().getMouseY(); }
}