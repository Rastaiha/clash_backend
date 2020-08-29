package clash.back.handler;

import clash.back.component.MessageRouter;
import clash.back.util.pathFinding.GameRouter;

import java.util.Timer;
import java.util.TimerTask;

public abstract class DefaultHandler {
    static MessageRouter messageRouter;
    static GameRouter gameRouter;
    protected Timer timer;
    public static long RELOAD_INTERVAL = 1000L;

    public static void setMessageRouter(MessageRouter messageRouter) {
        DefaultHandler.messageRouter = messageRouter;
    }

    public static void setGameRouter(GameRouter gameRouter) {
        DefaultHandler.gameRouter = gameRouter;
    }

    abstract void handle();

    public void init() {
        TimerTask pre = new TimerTask() {
            @Override
            public void run() {
                handle();
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(pre, RELOAD_INTERVAL, RELOAD_INTERVAL);
    }
}
