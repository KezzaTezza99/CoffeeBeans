package Engine.Managers;
import Engine.GameWindow;
import Engine.GenericUIComponents.InteractiveSplashScreen;
import Engine.Services.EventBusService;
import Engine.Services.TimerService;
import Game.Events.DrawDeathSplashscreen;
import Game.Events.PlayerTookDamage;
import Engine.GenericUIComponents.BasicSplashScreen;
import Engine.GenericUIComponents.StatBar;

import java.awt.*;

public class UIManager {
    private int health = 100;
    private final StatBar healthBar;
    //private final BasicSplashScreen deathSplashscreen;
    private final InteractiveSplashScreen deathSplashscreen;
    private boolean showHUD = true;
    private boolean showDeathSplashscreen = false;

    // TEMP
    private final GameWindow gameWindow;

    private boolean showDamage = false;
    private int hpToDisplay;
    private int xPos, yPos;
    private long damageTextEndTime = 0;

    // TODO: GameWindow is a temporary dependency
    public UIManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        healthBar = new StatBar(192, 16, 100, 32, 100);
        deathSplashscreen = new InteractiveSplashScreen(gameWindow, "YOU DIED");

        EventBusService.getBus().register(PlayerTookDamage.class, event -> {
            this.health = event.getNewHealth();
            healthBar.setCurrentValue(health);

            if(health == 0 || health < 0) showHUD = false;
        });

        EventBusService.getBus().register(DrawDeathSplashscreen.class, event -> displaySplashscreen(5000));
    }

    public void update() {
        if(System.currentTimeMillis() > damageTextEndTime) {
            showDamage = false;
        }
    }

    public void draw(Graphics2D graphics2D) {
        if(!showDeathSplashscreen) {
            if (showHUD) {
                drawHUD(graphics2D);
                drawScore(graphics2D);
            }

            if(showDamage) {
                drawHP(graphics2D);
            }
        } else {
            drawDeathSplashscreen(graphics2D);
        }
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

    private void drawHP(Graphics2D graphics2D) {
        graphics2D.setColor(Color.yellow);
        graphics2D.drawString(String.valueOf(hpToDisplay), xPos, yPos);
    }

    private void drawDeathSplashscreen(Graphics2D graphics2D) {
        deathSplashscreen.draw(graphics2D);
    }

    private void displaySplashscreen(long displayForMs) {
        setShowDeathSplashscreen(true);

        TimerService.getTimer().runAfterDelay(() -> {
            // TODO: We used to close the application but now we want the user to choose so eventually
            // we should fade this out of the engine, we don't need this call anymore we want the user to
            // decide if they want to quit the application, return to menu? or replay the level!
            System.out.println("It's been 5 seconds since we died... are we going to play again?");
        }, displayForMs);
    }

    public void displayHP(int hp, int xPos, int yPos, int durationInMs) {
        this.hpToDisplay = hp;
        this.xPos = xPos;
        this.yPos = yPos;
        this.damageTextEndTime = System.currentTimeMillis() + durationInMs;
        this.showDamage = true;
    }

    private void setShowDeathSplashscreen(boolean flag) { this.showDeathSplashscreen = flag; }
}