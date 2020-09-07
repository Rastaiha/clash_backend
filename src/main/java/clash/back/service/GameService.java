package clash.back.service;

import clash.back.component.MessageRouter;
import clash.back.domain.dto.RequestFightDto;
import clash.back.domain.entity.Fight;
import clash.back.domain.entity.Map;
import clash.back.domain.entity.Player;
import clash.back.domain.entity.building.Location;
import clash.back.domain.entity.building.MapEntity;
import clash.back.exception.CardNotFoundException;
import clash.back.exception.FighterNotAvailableException;
import clash.back.exception.PlayerNotFoundException;
import clash.back.handler.GlobalFightingHandler;
import clash.back.handler.MapHandler;
import clash.back.repository.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    CardRepository cardRepository;

    @Autowired
    MessageRouter messageRouter;

    MapHandler mapHandler;

    GlobalFightingHandler fightingHandler;

    public void handleFightRequest(RequestFightDto fightDto, Player host) throws PlayerNotFoundException, FighterNotAvailableException {
        Player guest = playerRepository.findPlayerByUsername(fightDto.getUsername().trim()).orElseThrow(PlayerNotFoundException::new);

//        guest = mapHandler.getWalkingPlayer(guest).orElseThrow(FighterNotAvailableException::new);
//        host = mapHandler.getWalkingPlayer(host).orElseThrow(FighterNotAvailableException::new);

//        if (guest.isReady() && guest.isNeighbourWith(host.getLocation()))//todo: uncomment for deploy
        fightingHandler.startNewFight(host, guest);
//        else throw new FighterNotAvailableException();
    }

    @Transactional
    public Map getMap() {
        Map map = mapRepository.findAll().iterator().next();
        List<MapEntity> byMapId = mapEntityRepository.findByMapId(map.getId());
        map.setMapEntities(byMapId);
        return map;
    }

    public void movePlayer(Location fromDto, Player player) {
//        mapHandler.addNewPlayerMovementHandler(player, fromDto);
        player.setLocation(fromDto);
        playerRepository.save(player);
    }

    public void putCard(String cardId, Player player) throws CardNotFoundException, FighterNotAvailableException {
        fightingHandler.putCard(cardRepository.findCardById(cardId).orElseThrow(CardNotFoundException::new), player);
    }

    public void finalizeFight(Fight fight) {
        // TODO draw fight
        fight.getWinner().getCivilization().addFightWinnerPrize();
        fight.getLoser().getCivilization().addFightLoserPrize();
        civilizationRepository.save(fight.getLoser().getCivilization());
        civilizationRepository.save(fight.getWinner().getCivilization());
    }
}
