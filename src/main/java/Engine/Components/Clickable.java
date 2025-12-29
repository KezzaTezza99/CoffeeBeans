package Engine.Components;

import Engine.Services.EventBusService;
import Engine.Services.GameContextService;
import Game.Events.DrawDamageTaken;

public interface Clickable {
    default void onClick() {
        handleClickEvent();
    }

    default void handleClickEvent() {
        EventBusService.getBus().post(new DrawDamageTaken(getXMousePos(), getYMousePos()));
    }

    default int getXMousePos() { return GameContextService.get().getMouseHandler().getMouseX(); }
    default int getYMousePos() { return GameContextService.get().getMouseHandler().getMouseY(); }
}