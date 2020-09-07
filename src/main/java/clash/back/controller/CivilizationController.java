package clash.back.controller;

import clash.back.domain.dto.CardDto;
import clash.back.domain.dto.CivilizationsFightDto;
import clash.back.domain.entity.Card;
import clash.back.service.CivilizationService;
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
        civilizationService.upgrade(userDetailsService.getUser().getCivilization());
    }
}
