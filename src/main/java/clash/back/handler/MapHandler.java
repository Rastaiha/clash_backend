package clash.back.handler;

import clash.back.domain.entity.Map;
import clash.back.util.pathFinding.GameRouter;

import java.util.HashSet;
import java.util.Set;

public class MapHandler extends DefaultHandler {
    static {
        RELOAD_INTERVAL = 1000L;
    }

    Map map;
    GameRouter router;
    Set<PlayerMovementHandler> playerMovementHandlers;

    public MapHandler(Map map) {
        this.map = map;
        router = new GameRouter(map);
        playerMovementHandlers = new HashSet<>();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    void handle() {
        playerMovementHandlers.removeIf(PlayerMovementHandler::isFinished);
        playerMovementHandlers.forEach(PlayerMovementHandler::handle);
    }

    public void addNewPlayerMovementHandler(PlayerMovementHandler playerMovementHandler) {
        playerMovementHandlers.stream().filter(playerMovementHandler1 -> false).forEach(pmh -> playerMovementHandlers.remove(pmh));
        playerMovementHandlers.add(playerMovementHandler);
    }
}
