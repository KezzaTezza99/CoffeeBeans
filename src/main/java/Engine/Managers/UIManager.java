package Engine.Managers;
import Engine.Entity.EntityType;
import Engine.Services.EventBusService;
import Engine.GenericUIComponents.StatBar;
import Engine.Utility.GameConstants;
import Game.Events.DamageTaken;
import java.awt.*;

public class UIManager {
    // UI Elements to draw on screen
    private final StatBar healthBar;
    private int hpToDisplayText;

    private int drawCurrentPlayerHealth = GameConstants.PLAYER_MAX_HEALTH;
    private final boolean showHUD = true;
    private boolean showDamage = false;

    private int xPos, yPos;
    private long damageTextEndTime = 0;

    public UIManager() {
        healthBar = new StatBar(192, 16, 100, 32, 100);

        EventBusService.getBus().register(DamageTaken.class, event -> {
            if(event.entity.getTag() != EntityType.PLAYER) return;
            this.drawCurrentPlayerHealth = event.getNewHealth();
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
        graphics2D.drawString("HP: " + drawCurrentPlayerHealth, 64, 32);

        // Health bar
        healthBar.draw(graphics2D);
    }

    private void drawScore(Graphics2D graphics2D) {
        graphics2D.setColor(Color.yellow);
        graphics2D.drawString("Score: 100", 64, 64);
    }

    private void drawHP(Graphics2D graphics2D) {
        graphics2D.setColor(Color.yellow);
        graphics2D.drawString(String.valueOf(hpToDisplayText), xPos, yPos);
    }

    public void displayDamageTaken(int hp, int xPos, int yPos, int durationInMs) {
        this.hpToDisplayText = hp;
        this.xPos = xPos;
        this.yPos = yPos;
        this.damageTextEndTime = System.currentTimeMillis() + durationInMs;
        this.showDamage = true;
    }

    public void resetHealthBar() {
        this.drawCurrentPlayerHealth = GameConstants.PLAYER_MAX_HEALTH;
        this.healthBar.setCurrentValue(this.drawCurrentPlayerHealth);
    }
}