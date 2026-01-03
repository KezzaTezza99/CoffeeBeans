package Engine.GenericUIComponents;

import Engine.GameWindow;
import Engine.Services.GameContextService;
import Engine.Utility.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

// TODO: Send an event to stop input and stuff, until user interacts, bonus this is a boolean so we can
// do on a game by game basis

public class DialogOverlay {
      private final String message;

      private int dialogX, dialogY, textX, textY;
      private int width, height;
      private Color backgroundColour = new Color(0, 0, 0, 200);
      private Color borderColour = Color.WHITE;

      public DialogOverlay(String message) {
          this.message = message;
          computeDefaultDialogPos();
     }

     // Default position will be bottom centre of the screen
    // TODO: This code isn't DRY as it is very very similar to SplashScreen creation
     private void computeDefaultDialogPos() {
         // Setting the dialog overlay location
         this.width = GameConstants.HALF_SCREEN_WIDTH;
         this.height = 200;
         this.dialogX = ((GameConstants.SCREEN_WIDTH / 2) - (width / 2));
         this.dialogY = ((GameConstants.SCREEN_HEIGHT / 2) + (height));

         // Setting the text position
         // TODO: would do this better and handle long text / multi-line etc.,
         this.textX = dialogX + 25;
         this.textY = dialogY + 35;
     }

     public void draw(Graphics2D graphics2D) {
          // TODO: Have a utility class that resets graphics
          Stroke oldStroke = graphics2D.getStroke();

          // TODO: Remove magic numbers, do we want default and then ability to pass this info in?
          graphics2D.setColor(backgroundColour);
          graphics2D.fillRoundRect(dialogX, dialogY, width, height, 35, 35);

          // Draw the border of dialog box
          graphics2D.setColor(borderColour);
          graphics2D.setStroke(new BasicStroke(5));
          graphics2D.drawRoundRect(dialogX + 5, dialogY + 5, width - 10, height - 10, 25, 25);

          // Drawing the text
          graphics2D.drawString(message, textX, textY);

          // Restore graphics state
          graphics2D.setStroke(oldStroke);
          graphics2D.dispose();
     }
}