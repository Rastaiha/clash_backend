package clash.back.service;

import clash.back.component.MessageRouter;
import clash.back.domain.dto.PlayerDto;
import clash.back.domain.entity.*;
import clash.back.exception.*;
import clash.back.repository.CardRepository;
import clash.back.repository.CardTypeRepository;
import clash.back.repository.CivilizationRepository;
import clash.back.repository.TreasuryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ArmoryService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    MessageRouter messageRouter;

    @Autowired
    CardTypeRepository cardTypeRepository;

    @Autowired
    TreasuryRepository treasuryRepository;

    public Card pickUpCard(Player player, String cardId) throws Exception {
        if (player.getStatus() != PlayerStatus.IN_TOWNHALL) {
            throw new NotInTownHallException();
        } else if (player.getCards().size() >= Player.BACKPACK_SIZE) {
            throw new FullBackpackException();
        }
        Card card = cardRepository.findCardById(cardId).orElseThrow(CardNotFoundException::new);
        if (!card.getCivilization().getId().equals(player.getCivilization().getId())) {
            throw new CardNotFoundException();
        } else if (card.getPlayer() != null) {
            throw new PickedUpCardException();
        }
        card.setPlayer(player);
        cardRepository.save(card);
        return card;
    }

    public void discardCard(Player player, String cardId) throws Exception {
        if (player.getStatus() != PlayerStatus.IN_TOWNHALL) {
            throw new NotInTownHallException();
        }
        Card card = cardRepository.findCardById(cardId).orElseThrow(CardNotFoundException::new);
        if (card.getPlayer() == null  || !card.getPlayer().getId().equals(player.getId())) {
            throw new NotInYourBackpackException();
        }
        card.setPlayer(null);
        cardRepository.save(card);
    }

    public Card buyCard(Player player, String cardTypeId) throws Exception {
        if (player.getStatus() != PlayerStatus.IN_TOWNHALL) {
            throw new NotInTownHallException();
        }
        CardType cardType = cardTypeRepository.findCardTypeById(cardTypeId).orElseThrow(CardTypeNotFoundException::new);
        if (!cardType.getAge().getId().equals(player.getCivilization().getAge().getId())) {
            throw new NotCorrectAgeException();
        } else if (player.getCivilization().getTreasury().getChivalry() < cardType.getChivalryCost()) {
            throw new NotEnoughResourcesException();
        }
        Card card = Card.builder().id(UUID.randomUUID().toString()).cardType(cardType).civilization(player.getCivilization()).level(0).build();
        cardRepository.save(card);
        Treasury treasury = player.getCivilization().getTreasury();
        treasury.decreaseChivalry(cardType.getChivalryCost());
        treasuryRepository.save(treasury);
        return card;
    }

    public void sellCard(Player player, String cardId) throws Exception {
        if (player.getStatus() != PlayerStatus.IN_TOWNHALL) {
            throw new NotInTownHallException();
        }
        Card card = cardRepository.findCardById(cardId).orElseThrow(CardNotFoundException::new);
        if (card.getPlayer() != null) {
            throw new PickedUpCardException();
        }
        Treasury treasury = player.getCivilization().getTreasury();
        treasury.increaseChivalry(card.getCardType().getChivalryValue());
        treasuryRepository.save(treasury);
        cardRepository.delete(card);
    }
}
