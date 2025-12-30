package Engine.Components;

import Engine.Entity.Entity;
import Engine.Entity.EntityType;
import Engine.Services.EventBusService;
import Engine.Services.GameContextService;
import Engine.Utility.GameConstants;
import Game.Events.DamageTaken;
import Game.Events.DrawDamageTaken;
import Game.Events.EntityDied;

public interface Clickable {
    default void onClick(Entity entity) {
        handleClickEvent(entity);
    }

    default void handleClickEvent(Entity entity) {
        EventBusService.getBus().post(new DrawDamageTaken(getXMousePos(), getYMousePos()));
        EventBusService.getBus().post(new DamageTaken(entity, GameConstants.PLAYER_DAMAGE_TO_ENEMY));

        if(entity.getHealth() <= 0) {
            EventBusService.getBus().post(new EntityDied(entity));
        }
    }

    default int getXMousePos() { return GameContextService.get().getMouseHandler().getMouseX(); }
    default int getYMousePos() { return GameContextService.get().getMouseHandler().getMouseY(); }
}