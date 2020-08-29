package clash.back.handler;

import clash.back.domain.dto.LocationDto;
import clash.back.domain.dto.PlayerMovementDto;
import clash.back.domain.entity.Player;
import clash.back.domain.entity.PlayerStatus;
import clash.back.domain.entity.building.Location;
import clash.back.util.pathFinding.Path;
import lombok.Getter;

import java.util.Objects;

@Getter
public class PlayerMovementHandler extends DefaultHandler {

    Player player;
    Location target;
    Path path;
    boolean finished;

    public PlayerMovementHandler(Player player, Location target) {
        this.player = player;
        this.target = target;
        this.init();
    }

    @Override
    public void init() {
        this.path = gameRouter.findRoute(player.getLocation(), target);
        player.setStatus(PlayerStatus.WALKING);
        System.out.println("path found, way to target: " + path.getPathLength());
    }

    @Override
    void handle() {
        if (path.hasNext() && player.isWalking()) {
            player.setLocation(path.getNextStation().getLocation());
            messageRouter.sendToCivilization(player.getCivilization(), new PlayerMovementDto().toDto(player));
        } else {
            player.setStatus(PlayerStatus.IDLE);
            finished = true;
        }
    }
}
