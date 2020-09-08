package clash.back.controller;

import clash.back.domain.dto.CardDto;
import clash.back.domain.dto.CivilizationDetailDto;
import clash.back.domain.dto.CivilizationsFightDto;
import clash.back.domain.entity.Card;
import clash.back.domain.entity.Civilization;
import clash.back.exception.CivilizationNotFoundException;
import clash.back.service.CivilizationService;
import clash.back.service.NotificationService;
import clash.back.service.PlayerService;
import clash.back.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/civilization")
public class CivilizationController {

    @Autowired
    CivilizationService civilizationService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    PlayerService playerService;

    @GetMapping
    public ResponseEntity<CivilizationDetailDto> getCivilizationsDetail() throws CivilizationNotFoundException {
        Civilization civilizationById = civilizationService.getCivilizationById(userDetailsService.getUser().getCivilization().getId());
        return ResponseEntity.ok((CivilizationDetailDto) new CivilizationDetailDto().toDto(civilizationById));
    }

    @GetMapping("/card")
    public ResponseEntity<Set<CardDto>> getCivilizationsCards() {
        List<Card> cards = civilizationService.getCards(userDetailsService.getUser().getCivilization());
        return ResponseEntity.ok(cards.stream().map(card -> (CardDto) new CardDto().toDto(card)).collect(Collectors.toSet()));
    }

    @PostMapping("/fight")
    public void fightWithCivilization(@RequestBody CivilizationsFightDto civilizationsFightDto) {

    }

    @PostMapping("/upgrade")
    public void upgrade() throws Exception {
        if (civilizationService.isReadyToUpgrade(userDetailsService.getUser().getCivilization()))
            civilizationService.upgrade(userDetailsService.getUser().getCivilization());
        else
            notificationService.sendUpgradeRequestNotification(userDetailsService.getUser().getCivilization(), userDetailsService.getUser());
    }

    @PostMapping("/accept_upgrade")
    public void acceptUpgradeRequest() throws Exception {
        playerService.acceptUpgradeRequest(userDetailsService.getUser());
        if (civilizationService.isReadyToUpgrade(userDetailsService.getUser().getCivilization())) upgrade();
    }
}
