package clash.back.controller;

import clash.back.configuration.LoginInterceptor;
import clash.back.domain.dto.*;
import clash.back.domain.entity.Card;
import clash.back.domain.entity.Player;
import clash.back.exception.FighterNotAvailableException;
import clash.back.exception.PlayerNotFoundException;
import clash.back.service.GameService;
import clash.back.service.PlayerService;
import clash.back.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @Autowired
    GameService gameService;

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public void init() {
        LoginInterceptor.playerController = this;
    }

    @GetMapping("/status")
    public ResponseEntity<PlayerDto> getPlayerDetails() throws Exception {
        return ResponseEntity.ok((PlayerDto) new PlayerDto().toDto(playerService.getPlayerDetails(userDetailsService.getUser().getId())));
    }

    public Player getPlayerByUsername(String username) throws Exception {
        return playerService.getPlayerDetails(username);
    }


    @PostMapping("/move")
    public void movePlayer(@RequestBody LocationDto locationDto) {
        if (!locationDto.isValid())
            return;
        gameService.movePlayer(locationDto.fromDto(), userDetailsService.getUser());
    }

    @PostMapping("/fight")
    public void requestFight(@RequestBody RequestFightDto fightDto) throws PlayerNotFoundException, FighterNotAvailableException {
        if (fightDto.isValid())
            gameService.handleFightRequest(fightDto, userDetailsService.getUser());
        else throw new PlayerNotFoundException();
    }

    @PatchMapping("/fight")
    public void putCard(@RequestBody FightCardDto cardDto) {
        if (cardDto.isValid())
            gameService.putCard(cardDto.getId(), userDetailsService.getUser());
    }

    @GetMapping("/card")
    public ResponseEntity<List<CardDto>> getPlayerCards() {
        Set<Card> cards = playerService.getPlayerCards(userDetailsService.getUser());
        return ResponseEntity.ok(cards.stream().map(card -> (CardDto) new CardDto().toDto(card)).collect(Collectors.toList()));
    }

}
