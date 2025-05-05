package Engine.Managers;
import Engine.Dispatcher.EventBusService;
import Game.Events.PlayerTookDamage;
import Game.UIElements.StatBar;

import java.awt.*;

public class UIManager {
    private int health = 100;
    private final StatBar healthBar;
    private boolean showHUD = true;

    public UIManager() {
        healthBar = new StatBar(192, 16, 100, 32, 100);

        EventBusService.getBus().register(PlayerTookDamage.class, event -> {
            this.health = event.getNewHealth();
            healthBar.setCurrentValue(health);

            if(health == 0 || health < 0) showHUD = false;
        });
    }

    public void draw(Graphics2D graphics2D) {
        if(showHUD) {
            drawHUD(graphics2D);
            drawScore(graphics2D);
        }
        graphics2D.dispose();
    }

    private void drawHUD(Graphics2D graphics2D) {
        // Health text
        graphics2D.setColor(Color.white);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24F));
        graphics2D.drawString("HP: " + health, 64, 32);

        // Health bar
        healthBar.draw(graphics2D);
    }

    private void drawScore(Graphics2D graphics2D) {
        graphics2D.setColor(Color.yellow);
        graphics2D.drawString("Score: 100", 64, 64);
    }
}