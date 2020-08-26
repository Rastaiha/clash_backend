package clash.back.service;

import clash.back.domain.entity.Player;
import clash.back.exception.PlayerNotFoundException;
import clash.back.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    public Player getPlayerDetails(String username){
        return playerRepository.findPlayerById(UUID.fromString(username)).get();
    }
}
