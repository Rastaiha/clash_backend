package clash.back.controller;

import clash.back.domain.dto.PlayerDto;
import clash.back.repository.PlayerRepository;
import clash.back.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping("/status/{username}")
    public ResponseEntity<PlayerDto> getPlayerDetails(@PathVariable String username) {
        return ResponseEntity.ok((PlayerDto) new PlayerDto().toDto(playerService.getPlayerDetails(username)));
    }

}
