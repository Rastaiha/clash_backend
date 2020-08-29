package clash.back.handler;

import clash.back.domain.entity.Player;

public class PlayerMovementHandler extends DefaultHandler {

    Player player;

    static {
        RELOAD_INTERVAL = 1000L;
    }

    @Override
    void handle() {
    }
}
