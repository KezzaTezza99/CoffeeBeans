package Engine.Managers;
import Engine.GameWindow;
import Engine.Services.EventBusService;
import Engine.Services.TimerService;
import Game.Events.DrawDeathSplashscreen;
import Game.Events.PlayerTookDamage;
import Engine.GenericUIComponents.SplashScreen;
import Engine.GenericUIComponents.StatBar;

import java.awt.*;

public class UIManager {
    private int health = 100;
    private final StatBar healthBar;
    private final SplashScreen deathSplashscreen;
    private boolean showHUD = true;
    private boolean showDeathSplashscreen = false;

    // TODO: GameWindow is a temporary dependency
    public UIManager(GameWindow gameWindow) {
        healthBar = new StatBar(192, 16, 100, 32, 100);
        deathSplashscreen = new SplashScreen(gameWindow, "YOU DIED");

        EventBusService.getBus().register(PlayerTookDamage.class, event -> {
            this.health = event.getNewHealth();
            healthBar.setCurrentValue(health);

            if(health == 0 || health < 0) showHUD = false;
        });

        EventBusService.getBus().register(DrawDeathSplashscreen.class, event -> displaySplashscreen(5000));
    }

    public void draw(Graphics2D graphics2D) {
        if(showHUD) {
            drawHUD(graphics2D);
            drawScore(graphics2D);
        }

        if(showDeathSplashscreen)
            drawDeathSplashscreen(graphics2D);

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

    private void drawDeathSplashscreen(Graphics2D graphics2D) {
        deathSplashscreen.draw(graphics2D);
    }

    private void displaySplashscreen(long displayForMs) {
        setShowDeathSplashscreen(true);

        TimerService.getTimer().runAfterDelay(() -> {
            System.exit(0);
        }, displayForMs);
    }

    private void setShowDeathSplashscreen(boolean flag) { this.showDeathSplashscreen = flag; }
}