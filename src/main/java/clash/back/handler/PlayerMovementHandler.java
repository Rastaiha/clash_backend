package clash.back.handler;

import clash.back.domain.dto.PlayerMovementDto;
import clash.back.domain.entity.Player;
import clash.back.domain.entity.PlayerStatus;
import clash.back.domain.entity.building.Location;
import clash.back.service.PlayerService;
import clash.back.util.Settings;
import clash.back.util.pathFinding.Path;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerMovementHandler extends DefaultHandler {

    Player player;
    Location target;
    Path path;
    boolean finished, finalized;
    PlayerService playerService;

    public PlayerMovementHandler(Player player, Location target, PlayerService playerService) {
        this.player = player;
        this.target = target;
        this.playerService = playerService;
        this.init();
    }

    @Override
    public void init() {
        this.path = gameRouter.findRoute(player.getLocation(), target);
        player.setStatus(PlayerStatus.WALKING);
        playerService.updatePlayer(player);
        finished = false;
        finalized = false;
        logger.info("path found, way to target: " + path.getPathLength());
        this.handle();
    }

    @Override
    void handle() {
        if (path.hasNext() && player.isWalking()) {
            player.setLocation(path.getNextStation().getLocation());
            messageRouter.sendToAll(new PlayerMovementDto().toDto(player), Settings.WS_MAP_DEST);
            // TODO: 30.08.20 move this functionality to MapHandler, players shouldn't announce their location
        } else {
            player.setStatus(PlayerStatus.IDLE);
            player.setLocation(target);
            finished = true;
            finalized = false;
        }
    }
}
