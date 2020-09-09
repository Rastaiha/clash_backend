package clash.back.handler;

import clash.back.domain.entity.Map;
import clash.back.domain.entity.Player;
import clash.back.domain.entity.PlayerStatus;
import clash.back.domain.entity.building.Location;
import clash.back.domain.entity.building.MapEntity;
import clash.back.service.PlayerService;
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
    PlayerService playerService;

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
        playerMovementHandlers.stream().filter(PlayerMovementHandler::isFinished).forEach(playerMovementHandler ->
                playerService.updatePlayerLocation(updatePlayerStatus(playerMovementHandler.getPlayer())));
    }

    public void addNewPlayerMovementHandler(Player player, Location to) {
//        updatePlayerStatus(player);
//        messageRouter.sendToAll(new PlayerMovementDto().toDto(player), Settings.WS_MAP_DEST);
        PlayerMovementHandler handler = playerMovementHandlers.stream()
                .filter(playerMovementHandler -> playerMovementHandler.getPlayer().getId().equals(player.getId())).
                        findAny().orElse(new PlayerMovementHandler(player, to, playerService));

        handler.setTarget(to);
        handler.init();
        playerMovementHandlers.add(handler);

    }

    private Player updatePlayerStatus(Player player) {
        Optional<MapEntity> entity = map.getMapEntities().stream().filter(mapEntity -> mapEntity.getX() == player.getX()).filter(mapEntity -> mapEntity.getY() == player.getY()).findAny();
        if (entity.isPresent())
            switch (entity.get().getClass().getSimpleName().trim().toUpperCase()) {
                case "MOTEL":
                    player.setStatus(PlayerStatus.RESTING);
                    break;
                case "INSTITUTE":
                    player.setStatus(PlayerStatus.IN_INSTITUTE);
                    break;
                case "TOWNHALL":
                    player.setStatus(PlayerStatus.IN_TOWNHALL);
                    break;
                default:
                    player.setStatus(PlayerStatus.IDLE);
            }
        return player;
    }

    public Optional<Player> getWalkingPlayer(Player player) {
        Optional<PlayerMovementHandler> handler = playerMovementHandlers.stream().filter(playerMovementHandler -> playerMovementHandler.getPlayer().getId().equals(player.getId())).findAny();
        return handler.map(PlayerMovementHandler::getPlayer);
    }
}
