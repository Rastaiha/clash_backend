package clash.back.controller;


import clash.back.domain.dto.CardDto;
import clash.back.domain.dto.CardTypeDto;
import clash.back.domain.entity.Card;
import clash.back.service.ArmoryService;
import clash.back.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/armory")
public class ArmoryController {
    @Autowired
    ArmoryService armoryService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @GetMapping("/cardtype")
    public ResponseEntity<List<CardTypeDto>> getCardTypes() {
        return ResponseEntity.ok(armoryService.getCardTypes(userDetailsService.getUser()).stream()
                .map(cardType -> (CardTypeDto) new CardTypeDto().toDto(cardType)).collect(Collectors.toList()));
    }

    @PostMapping("/cardtype/{cardTypeID}/buy")
    public ResponseEntity<CardDto> buyCard(@PathVariable String cardTypeID) throws Exception {
        return ResponseEntity.ok((CardDto) new CardDto().toDto(armoryService.buyCard(userDetailsService.getUser(), cardTypeID)));
    }

    @PostMapping("/card/{cardID}/pickup")
    public void pickUpCard(@PathVariable String cardID) throws Exception {
        armoryService.pickUpCard(userDetailsService.getUser(), cardID);
    }

    @PostMapping("/card/{cardID}/discard")
    public void discardCard(@PathVariable String cardID) throws Exception {
        armoryService.discardCard(userDetailsService.getUser(), cardID);
    }

    @PostMapping("/card/{cardID}/sell")
    public void sellCard(@PathVariable String cardID) throws Exception {
        armoryService.sellCard(userDetailsService.getUser(), cardID);
    }

    @GetMapping("/card")
    public ResponseEntity<List<CardDto>> getPlayerCards() {
        Set<Card> cards = armoryService.getPlayerCards(userDetailsService.getUser());
        return ResponseEntity.ok(cards.stream().map(card -> (CardDto) new CardDto().toDto(card)).collect(Collectors.toList()));
    }
}
