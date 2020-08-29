package clash.back.service;

import clash.back.component.MessageRouter;
import clash.back.domain.dto.LocationDto;
import clash.back.domain.dto.PlayerMovementDto;
import clash.back.domain.entity.Player;
import clash.back.domain.entity.building.Location;
import clash.back.exception.PlayerNotFoundException;
import clash.back.handler.MapHandler;
import clash.back.repository.MapRepository;
import clash.back.repository.PlayerRepository;
import clash.back.repository.WorldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    MapRepository mapRepository;

    @Autowired
    MessageRouter messageRouter;

    public Player getPlayerDetails(String username) throws Exception {
        return playerRepository.findPlayerByUsername(username).orElseThrow(PlayerNotFoundException::new);
    }

    public void movePlayer(Location fromDto, Player player) {
//        player.setLocation(fromDto);
//        playerRepository.save(player);
        new MapHandler(mapRepository.findAll().iterator().next()).init();
        messageRouter.sendToCivilization(player.getCivilization(), new PlayerMovementDto().toDto(player));
    }
}
