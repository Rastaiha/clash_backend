package clash.back.handler;

import clash.back.component.MessageRouter;

import java.util.Timer;
import java.util.TimerTask;

public abstract class DefaultHandler {
    static MessageRouter messageRouter;
    protected Timer timer;
    public static long RELOAD_INTERVAL = 1000L;

    public static void setMessageRouter(MessageRouter messageRouter) {
        DefaultHandler.messageRouter = messageRouter;
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
