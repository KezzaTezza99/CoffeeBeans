package Engine;
import Engine.Entity.NPC;
import Engine.Entity.Player;
import Engine.Entity.Enemy;
import Engine.Input.KeyHandler;
import Engine.Input.MouseHandler;
import Engine.Managers.*;
import Engine.Managers.UIManager;
import Engine.Services.FontService;
import Engine.Services.GameContextService;
import Engine.States.STATES;
import Engine.Utility.GameConstants;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JPanel implements Runnable {
    String title = "CoffeeBeans";
    Thread mainThread;
    private boolean isRunning = false;

    // TODO: COULD WE HANDLE THIS IN GAME STATE MANAGER ? OR SHOULD WE HAVE A GAME CLASS?
    public Player player;
    public Enemy enemy;
    public Enemy enemy2;
    public Enemy enemy3;
    public NPC npc;

    public boolean replayGame = false;

    public GameWindow() {
        // Setting up the game window
        this.setPreferredSize(new Dimension(GameConstants.SCREEN_WIDTH,GameConstants.SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        FontService.initialiseFonts();

        init(STATES.MAIN_MENU);
    }

    public void init(STATES stateToInit) {
        isRunning = true;
        KeyHandler keyHandler = new KeyHandler(this);
        MouseHandler mouseHandler = new MouseHandler(this);
        TileManager tileManager = new TileManager(false);
        CollisionManager collisionManager = new CollisionManager();
        UIManager uiManager = new UIManager();
        SoundManager soundManager = new SoundManager();
        EntityManager entityManager = new EntityManager();
        GameStateManager gameStateManager = new GameStateManager(stateToInit);

        GameContextService.initGameContext(
                this,
                tileManager,
                collisionManager,
                uiManager,
                soundManager,
                entityManager,
                keyHandler,
                mouseHandler,
                gameStateManager);

        createEntities();
    }

    public void resetWorld() {
        cleanup();

        GameContextService.get().getUiManager().resetHealthBar();
        GameContextService.get().getGameStateManager().pauseFlag = false;

        createEntities();
    }

    // Override that is called when the JPanel is created
    public void addNotify() {
        super.addNotify();

        // If we haven't started this thread, start one
        if(mainThread == null) {
            mainThread = new Thread(this, "GameThread");
            mainThread.start();
        }
    }

    @Override
    public void run() {
        // Time step for limiting FPS
        double drawInterval = 1000000000.0 / GameConstants.FPS; // 1 second / FPS
        double delta = 0;
        long lastTime = System.nanoTime();

        while (isRunning) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                // Update game state and redraw
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        GameContextService.get().getKeyHandler().tick();
        GameContextService.get().getGameStateManager().input(GameContextService.get().getKeyHandler(), GameContextService.get().getMouseHandler());
        GameContextService.get().getGameStateManager().update();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        GameContextService.get().getGameStateManager().draw(graphics2D);
        graphics2D.dispose();
    }

    private void createEntities() {
        // Construct the entities now game context is ready
        player = new Player();
        enemy = new Enemy();
        enemy2 = new Enemy(128 * 4, 128);
        enemy3 = new Enemy(128 * 3, 128);
        npc = new NPC();

        // Register the entities
        GameContextService.get().getEntityManager().addEntity(player);
        GameContextService.get().getEntityManager().addEntity(enemy);
        GameContextService.get().getEntityManager().addEntity(enemy2);
        GameContextService.get().getEntityManager().addEntity(enemy3);
        GameContextService.get().getEntityManager().addEntity(npc);
    }

    private void cleanup() {
        GameContextService.get().getSoundManager().closeAll(false);
        GameContextService.get().getEntityManager().removeAllEntities();
    }
}