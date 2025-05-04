package Engine.Managers;
import Engine.Dispatcher.EventBusService;
import Game.Events.PlayerTookDamage;
import java.awt.*;

public class UIManager {
    private int health = 100;

    public UIManager() {
        EventBusService.getBus().register(PlayerTookDamage.class, event -> {
            this.health = event.getNewHealth();
        });
    }

    public void draw(Graphics2D graphics2D) {
        drawHUD(graphics2D);
    }

    private void drawHUD(Graphics2D graphics2D) {
        graphics2D.setColor(Color.white);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24F));
        graphics2D.drawString("HP: " + health, 64, 64);
    }
}