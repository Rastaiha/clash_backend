package clash.back.service;

import clash.back.component.MessageRouter;
import clash.back.domain.dto.RequestFightDto;
import clash.back.domain.entity.Fight;
import clash.back.domain.entity.Map;
import clash.back.domain.entity.Player;
import clash.back.domain.entity.building.Location;
import clash.back.exception.FighterNotAvailableException;
import clash.back.exception.PlayerNotFoundException;
import clash.back.handler.FightHandler;
import clash.back.handler.MapHandler;
import clash.back.handler.PlayerMovementHandler;
import clash.back.repository.CivilizationRepository;
import clash.back.repository.MapEntityRepository;
import clash.back.repository.MapRepository;
import clash.back.repository.PlayerRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

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

    public void handleFightRequest(RequestFightDto fightDto, Player host) throws PlayerNotFoundException, FighterNotAvailableException {
        Player guest = playerRepository.findPlayerByUsername(fightDto.getUsername().trim()).orElseThrow(PlayerNotFoundException::new);

        if (guest.isReady() && guest.isNeighbourWith(host.getLocation()))
            new FightHandler(Fight.builder().guest(guest).host(host).startTime(new Date().getTime()).build()).init();
        else throw new FighterNotAvailableException();
    }

    @Transactional
    public Map getMap() {
        return mapRepository.findAll().iterator().next();
    }


    public void movePlayer(Location fromDto, Player player) {
        mapHandler.addNewPlayerMovementHandler(new PlayerMovementHandler(player, fromDto));
    }
}
