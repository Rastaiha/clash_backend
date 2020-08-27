package clash.back.controller;

import clash.back.configuration.LoginInterceptor;
import clash.back.configuration.StompPrincipal;
import clash.back.domain.dto.LocationDto;
import clash.back.domain.dto.PlayerDto;
import clash.back.domain.entity.Player;
import clash.back.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @Autowired
    SimpMessagingTemplate template;

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

    @MessageMapping("/move")
    public void movePlayer(@RequestBody LocationDto locationDto, StompPrincipal principal) {
        if (!locationDto.isValid())
            return;
        playerService.movePlayer(locationDto.fromDto(), principal.getPlayer());
    }


}
