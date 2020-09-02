package clash.back.handler;

import clash.back.domain.entity.Map;
import clash.back.domain.entity.Player;
import clash.back.domain.entity.building.Location;
import clash.back.util.pathFinding.GameRouter;

import java.util.HashSet;
import java.util.Optional;
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
        playerMovementHandlers.forEach(PlayerMovementHandler::handle);
    }

    public void addNewPlayerMovementHandler(Player player, Location to) {
        PlayerMovementHandler handler = playerMovementHandlers.stream()
                .filter(playerMovementHandler -> playerMovementHandler.getPlayer().getId().equals(player.getId())).
                        findAny().orElse(new PlayerMovementHandler(player, to));

        handler.setTarget(to);
        handler.init();
        playerMovementHandlers.add(handler);
    }

    public Optional<Player> getWalkingPlayer(Player player) {
        Optional<PlayerMovementHandler> handler = playerMovementHandlers.stream().filter(playerMovementHandler -> playerMovementHandler.getPlayer().getId().equals(player.getId())).findAny();
        return handler.map(PlayerMovementHandler::getPlayer);
    }
}
