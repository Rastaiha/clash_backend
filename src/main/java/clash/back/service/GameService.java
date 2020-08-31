package clash.back.service;

import clash.back.component.MessageRouter;
import clash.back.domain.dto.RequestFightDto;
import clash.back.domain.entity.Map;
import clash.back.domain.entity.Player;
import clash.back.domain.entity.building.Location;
import clash.back.exception.FighterNotAvailableException;
import clash.back.exception.PlayerNotFoundException;
import clash.back.handler.GlobalFightingHandler;
import clash.back.handler.MapHandler;
import clash.back.repository.CivilizationRepository;
import clash.back.repository.MapEntityRepository;
import clash.back.repository.MapRepository;
import clash.back.repository.PlayerRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Setter
public class GameService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    CivilizationRepository civilizationRepository;

    @Autowired
    MapRepository mapRepository;

    @Autowired
    MapEntityRepository mapEntityRepository;

    @Autowired
    MessageRouter messageRouter;

    MapHandler mapHandler;

    GlobalFightingHandler fightingHandler;

    public void handleFightRequest(RequestFightDto fightDto, Player host) throws PlayerNotFoundException, FighterNotAvailableException {
        Player guest = playerRepository.findPlayerByUsername(fightDto.getUsername().trim()).orElseThrow(PlayerNotFoundException::new);

        if (guest.isReady() && guest.isNeighbourWith(host.getLocation()))
            fightingHandler.startNewFight(host, guest);
        else throw new FighterNotAvailableException();
    }

    @Transactional
    public Map getMap() {
        return mapRepository.findAll().iterator().next();
    }


    public void movePlayer(Location fromDto, Player player) {
        mapHandler.addNewPlayerMovementHandler(player, fromDto);
    }

    public void putCard(String cardId, Player player) {

    }
}
