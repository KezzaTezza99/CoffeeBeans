package Engine.GenericUIComponents;

import Engine.GameWindow;

import java.awt.*;
import java.awt.image.BufferedImage;

// TODO: Send an event to stop input and stuff, until user interacts, bonus this is a boolean so we can
// do on a game by game basis

public class DialogOverlay {
    // TODO: Again injecting game window for temp
      private final GameWindow gameWindow;
      private final String message;

      private int dialogX, dialogY, textX, textY;
      private int width, height;
      private Color backgroundColour = new Color(0, 0, 0, 200);
      private Color borderColour = Color.WHITE;
      private final Font font;

      public DialogOverlay(GameWindow gameWindow, String message) {
          this.gameWindow = gameWindow;
          this.message = message;
          this.font = new Font("Default", Font.BOLD, 28);

          computeDefaultDialogPos();
     }

     // Default position will be bottom centre of the screen
    // TODO: This code isn't DRY as it is very very similar to SplashScreen creation
     private void computeDefaultDialogPos() {
//         BufferedImage temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
//         Graphics2D graphics2D = temp.createGraphics();
//
//         graphics2D.setFont(this.font);
//
//         FontMetrics metrics = graphics2D.getFontMetrics();

         // Setting the dialog overlay location
         this.width = gameWindow.getHalfScreenWidth();
         this.height = 200;
         this.dialogX = ((gameWindow.getScreenWidth() / 2) - (width / 2));
         this.dialogY = ((gameWindow.getScreenHeight() / 2) + (height));

         // Setting the text position
         // TODO: would do this better and handle long text / multi-line etc.,
         this.textX = dialogX + 25;
         this.textY = dialogY + 35;

//         graphics2D.dispose();
     }

     public void draw(Graphics2D graphics2D) {
          // TODO: Remove magic numbers, do we want default and then ability to pass this info in?
         graphics2D.setColor(backgroundColour);
         graphics2D.fillRoundRect(dialogX, dialogY, width, height, 35, 35);

         // Draw the border of dialog box
         graphics2D.setColor(borderColour);
         graphics2D.setStroke(new BasicStroke(5));
         graphics2D.drawRoundRect(dialogX + 5, dialogY + 5, width - 10, height - 10, 25, 25);

         // Drawing the text
         graphics2D.drawString(message, textX, textY);
     }
}