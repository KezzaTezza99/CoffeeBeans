package Engine.Services;

import Engine.Utility.Fonts;

public final class FontService {
    private FontService() {}
    private static Fonts allFonts;

    public static void initialiseFonts() {
        if(allFonts != null) {
            throw new IllegalStateException("Fonts already initialized");
        }
        allFonts = new Fonts();
    }

    public static Fonts getFonts() {
        if(allFonts == null) {
            throw new IllegalStateException("Fonts not initialized yet!");
        }
        return allFonts;
    }
}