package Engine.Services;

import Engine.Utility.Timer;

public class TimerService {
    private static final Timer timer = new Timer();

    public static Timer getTimer() { return timer; }
}
