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
    int i;

    public MapHandler(Map map, PlayerService playerService) {
        this.map = map;
        this.playerService = playerService;
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
        if (i % 30 == 29)
            for (PlayerMovementHandler playerMovementHandler : playerMovementHandlers) {
                logger.info("syncing");
                Player player = playerMovementHandler.getPlayer();
                player.setLocation(playerMovementHandler.target);
                player.setStatus(getPlayerStatus(player));
                playerService.updatePlayer(player);
            }
    }

    public void addNewPlayerMovementHandler(Player player, Location to) {
//        updatePlayerStatus(player);
//        messageRouter.sendToAll(new PlayerMovementDto().toDto(player), Settings.WS_MAP_DEST);
        PlayerMovementHandler handler = playerMovementHandlers.stream()
                .filter(playerMovementHandler -> playerMovementHandler.getPlayer().getId().equals(player.getId())).
                        findAny().orElse(new PlayerMovementHandler(player, to, playerService));


        playerMovementHandlers.add(handler);
    }

    private PlayerStatus getPlayerStatus(Player player) {
        Optional<MapEntity> entity = map.getMapEntities().stream().filter(mapEntity -> mapEntity.getX() == player.getX()).filter(mapEntity -> mapEntity.getY() == player.getY()).findAny();
        if (entity.isPresent())
            switch (entity.get().getClass().getSimpleName().trim().toUpperCase()) {
                case "MOTEL":
                    return PlayerStatus.RESTING;
                case "INSTITUTE":
                    return PlayerStatus.IN_INSTITUTE;
                case "TOWNHALL":
                    return PlayerStatus.IN_TOWNHALL;
                default:
                    return PlayerStatus.IDLE;
            }
        return PlayerStatus.IDLE;
    }

    public Optional<Player> getWalkingPlayer(Player player) {
        Optional<PlayerMovementHandler> handler = playerMovementHandlers.stream().filter(playerMovementHandler -> playerMovementHandler.getPlayer().getId().equals(player.getId())).findAny();
        return handler.map(PlayerMovementHandler::getPlayer);
    }
}
