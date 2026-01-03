package Engine.Utility;

import java.awt.*;

public class Fonts {
    private Font defaultFont;
    private Font smallerFont;
    private Font dialogFont;

    public Fonts() {
        defaultFont = new Font("Default", Font.BOLD, 48);
        smallerFont = new Font("Smaller Default", Font.BOLD, 22);
        dialogFont = new Font("Default", Font.BOLD, 28);
    }

    public Font getDefaultFont() { return defaultFont; }
    public Font getSmallerFont() { return smallerFont; }
    public Font getDialogFont() { return dialogFont; }
}