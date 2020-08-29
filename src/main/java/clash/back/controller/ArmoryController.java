package clash.back.controller;


import clash.back.configuration.StompPrincipal;
import clash.back.domain.dto.CardDto;
import clash.back.domain.dto.PlayerDto;
import clash.back.exception.PlayerNotFoundException;
import clash.back.service.ArmoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/armory")
public class ArmoryController {
    @Autowired
    ArmoryService armoryService;

//    @PostMapping("/buy/{cardTypeID}")
//    public ResponseEntity<CardDto> buyCard(@PathVariable String cardTypeID) throws Exception {
//        return ResponseEntity.ok((CardDto) new CardDto().toDto(armoryService.buyCard(principal.getPlayer(), cardTypeID)));
//    }
//
//    @PostMapping("/pickup/{cardID}")
//    public ResponseEntity<PlayerDto> pickUpCard(@PathVariable String cardID) throws Exception {
//        armoryService.pickUpCard(principal.getPlayer(), cardID);
//        return ResponseEntity.ok((PlayerDto) new PlayerDto().toDto(principal.getPlayer()));
//    }
}
