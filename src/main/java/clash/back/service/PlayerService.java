package clash.back.service;

import clash.back.component.MessageRouter;
import clash.back.domain.entity.Card;
import clash.back.domain.entity.Player;
import clash.back.exception.PlayerNotFoundException;
import clash.back.repository.MapRepository;
import clash.back.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    public Set<Card> getPlayerCards(Player player) {
        return player.getCards();
    }
}
