package Engine.Utility;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Timer {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void runNow(Runnable task) {
        scheduler.execute(task);
    }

    public void runAfterDelay(Runnable task, long delayMs) {
        scheduler.schedule(task, delayMs, TimeUnit.MILLISECONDS);
    }

    public void runRepeating(Runnable task, long initialDelayMs, long periodMs) {
        scheduler.scheduleAtFixedRate(task, initialDelayMs, periodMs, TimeUnit.MILLISECONDS);
    }

    public boolean isRunning() {
        return !scheduler.isShutdown();
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }
}