package Engine.Managers;
import Engine.Entity.EntityType;
import Engine.GameWindow;
import Engine.Services.EventBusService;
import Engine.GenericUIComponents.StatBar;
import Game.Events.DamageTaken;

import java.awt.*;

// TODO: We could change our counter for damage attacks to follow this approach used here

public class UIManager {
    private int health = 100;
    private StatBar healthBar;
    private boolean showHUD = true;
    private boolean showDamage = false;
    private int hpToDisplay;
    private int xPos, yPos;
    private long damageTextEndTime = 0;

    public UIManager() {
        healthBar = new StatBar(192, 16, 100, 32, 100);

        EventBusService.getBus().register(DamageTaken.class, event -> {
            if(event.entity.getTag() != EntityType.PLAYER) return;
            this.health = event.getNewHealth();
            healthBar.setCurrentValue(event.getNewHealth());
        });
    }

    public void update() {
        if(System.currentTimeMillis() > damageTextEndTime) {
            showDamage = false;
        }
    }

    public void draw(Graphics2D graphics2D) {
        if (showHUD) {
            drawHUD(graphics2D);
            drawScore(graphics2D);
        }

        if(showDamage) {
            drawHP(graphics2D);
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

    public void displayDamageTaken(int hp, int xPos, int yPos, int durationInMs) {
        this.hpToDisplay = hp;
        this.xPos = xPos;
        this.yPos = yPos;
        this.damageTextEndTime = System.currentTimeMillis() + durationInMs;
        this.showDamage = true;
    }

    public void resetHealthBar() {
//        EventBusService.getBus().register(PlayerTookDamage.class, event -> {
//            this.health = event.getNewHealth();
//            healthBar.setCurrentValue(health);
//
//            if(health == 0 || health < 0) showHUD = false;
//        });
        //TODO: NEEDED?
        System.out.println("POOP");
    }
}