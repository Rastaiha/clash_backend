package clash.back.service;

import clash.back.component.MessageRouter;
import clash.back.domain.dto.LocationDto;
import clash.back.domain.dto.PlayerMovementDto;
import clash.back.domain.entity.Player;
import clash.back.domain.entity.building.Location;
import clash.back.exception.PlayerNotFoundException;
import clash.back.repository.PlayerRepository;
import clash.back.repository.WorldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    MessageRouter messageRouter;

    public Player getPlayerDetails(String username) throws Exception {
        return playerRepository.findPlayerByUsername(username).orElseThrow(PlayerNotFoundException::new);
    }

    public void movePlayer(Location fromDto, Player player) {
        player.setLocation(fromDto);
        playerRepository.save(player);
        messageRouter.sendToCivilization(player.getCivilization(), new PlayerMovementDto().toDto(player));
    }
}
