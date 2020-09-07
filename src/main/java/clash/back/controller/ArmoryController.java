package clash.back.controller;


import clash.back.domain.dto.CardDto;
import clash.back.domain.dto.CardTypeDto;
import clash.back.service.ArmoryService;
import clash.back.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/armory")
public class ArmoryController {
    @Autowired
    ArmoryService armoryService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @GetMapping("/cardtype")
    public ResponseEntity<List<CardTypeDto>> getCardTypes() {
        return ResponseEntity.ok(StreamSupport.stream(armoryService.getCardTypes().spliterator(), false)
                .map(cardType -> (CardTypeDto) new CardTypeDto().toAgeAdoptedDto(cardType, userDetailsService.getUser().getCivilization().getAge())).collect(Collectors.toList()));
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

    @PostMapping("/card/{cardID}/upgrade")
    public ResponseEntity<CardDto> upgradeCard(@PathVariable String cardID) throws Exception {
        return ResponseEntity.ok((CardDto) new CardDto().toDto(armoryService.upgradeCard(userDetailsService.getUser(), cardID)));
    }

}
