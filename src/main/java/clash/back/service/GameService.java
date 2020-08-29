package clash.back.service;

import clash.back.component.MessageRouter;
import clash.back.domain.dto.PlayerMovementDto;
import clash.back.domain.dto.RequestFightDto;
import clash.back.domain.entity.Fight;
import clash.back.domain.entity.Map;
import clash.back.domain.entity.Player;
import clash.back.domain.entity.building.Location;
import clash.back.exception.PlayerNotFoundException;
import clash.back.handler.FightHandler;
import clash.back.handler.MapHandler;
import clash.back.handler.PlayerMovementHandler;
import clash.back.repository.CivilizationRepository;
import clash.back.repository.MapRepository;
import clash.back.repository.PlayerRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    MessageRouter messageRouter;

    MapHandler mapHandler;

    public void handleFightRequest(RequestFightDto fightDto, Player host) throws PlayerNotFoundException {
        System.out.println(this.playerRepository);
        System.out.println(fightDto.getUsername());
        Player guest = playerRepository.findPlayerByUsername(fightDto.getUsername().trim()).orElseThrow(PlayerNotFoundException::new);

        if (guest.isReady())
            new FightHandler(Fight.builder().guest(guest).host(host).startTime(new Date().getTime()).build()).init();
    }

    public Map getMap() {
        return mapRepository.findAll().iterator().next();
    }


    public void movePlayer(Location fromDto, Player player) {
        mapHandler.addNewPlayerMovementHandler(new PlayerMovementHandler(player, fromDto));
//        messageRouter.sendToCivilization(player.getCivilization(), new PlayerMovementDto().toDto(player));
    }
}
