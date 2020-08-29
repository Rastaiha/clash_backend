package clash.back.controller;

import clash.back.configuration.LoginInterceptor;
import clash.back.domain.dto.LocationDto;
import clash.back.domain.dto.PlayerDto;
import clash.back.domain.dto.RequestFightDto;
import clash.back.domain.entity.Player;
import clash.back.exception.PlayerNotFoundException;
import clash.back.service.GameService;
import clash.back.service.PlayerService;
import clash.back.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/status/{username}")
    public ResponseEntity<PlayerDto> getPlayerDetails(@PathVariable String username) throws Exception {
        return ResponseEntity.ok((PlayerDto) new PlayerDto().toDto(playerService.getPlayerDetails(username)));
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

    @PostMapping("/request")
    public void requestFight(@RequestBody RequestFightDto fightDto) throws PlayerNotFoundException {
        gameService.handleFightRequest(fightDto, userDetailsService.getUser());
    }


}
