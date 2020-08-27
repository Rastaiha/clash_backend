package clash.back.service;

import clash.back.domain.dto.RequestFightDto;
import clash.back.domain.entity.Fight;
import clash.back.domain.entity.Player;
import clash.back.exception.PlayerNotFoundException;
import clash.back.handler.FightHandler;
import clash.back.repository.CivilizationRepository;
import clash.back.repository.MapRepository;
import clash.back.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GameService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    CivilizationRepository civilizationRepository;

    @Autowired
    MapRepository mapRepository;

    public void handleFightRequest(RequestFightDto fightDto, Player host) throws PlayerNotFoundException {
        System.out.println(this.playerRepository);
        System.out.println(fightDto.getUsername());
        Player guest = playerRepository.findPlayerByUsername(fightDto.getUsername().trim()).orElseThrow(PlayerNotFoundException::new);

        if (guest.isReady())
            new FightHandler(Fight.builder().guest(guest).host(host).startTime(new Date().getTime()).build()).init();
    }
}
