package clash.back.service;

import clash.back.domain.entity.Player;
import clash.back.domain.entity.building.Location;
import clash.back.exception.PlayerNotFoundException;
import clash.back.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    public Player getPlayerDetails(String username) throws Exception {
        return playerRepository.findPlayerByUsername(username).orElseThrow(PlayerNotFoundException::new);
    }

    public void movePlayer(Location fromDto, Player player) {
        player.setLocation(fromDto);
        playerRepository.save(player);
    }
}
