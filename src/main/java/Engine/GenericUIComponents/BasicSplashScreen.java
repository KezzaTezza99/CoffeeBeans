//package Engine.GenericUIComponents;
//import Engine.GameWindow;
//import Engine.Services.GameContextService;
//import Engine.Utility.GameConstants;
//import Engine.Utility.TextPositionHelper;
//
//import java.awt.*;
//
//public class BasicSplashScreen {
//    private final String message;
//
//    private final int x;
//    private final int y;
//    // TODO: Font set globally? ServiceLocator?
//    private final Font font;
//
//    public BasicSplashScreen(String message) {
//        this.message = message;
//        this.font = new Font("Default", Font.BOLD, 48);
//
//        Point centrePoint = TextPositionHelper.getCentredTextPos(message, font, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
//        this.x = centrePoint.x;
//        this.y = centrePoint.y;
//    }
//
//    public void draw(Graphics2D graphics2D) {
//        // Drawing a semi-transparent overlay
//        graphics2D.setColor(new Color(0, 0,0,150));
//        graphics2D.fillRect(0, 0, GameContextService.get().getGameWindow().getWidth(), GameContextService.get().getGameWindow().getHeight());
//
//        // Drawing centred text
//        graphics2D.setColor(Color.WHITE);
//        graphics2D.setFont(this.font);
//        graphics2D.drawString(message, x, y);
//    }
//}