package clash.back.service;

import clash.back.domain.entity.*;
import clash.back.exception.*;
import clash.back.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArmoryService {
//  TODO consider location
    @Autowired
CardRepository cardRepository;

    @Autowired
    CardTypeRepository cardTypeRepository;

    @Autowired
    TreasuryRepository treasuryRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ArmoryRepository armoryRepository;

    public Set<CardType> getCardTypes(Player player) {
        return player.getCivilization().getAge().getCardTypes();
    }

    public Card pickUpCard(Player player, String cardId) throws Exception {
        if (player.getCards().size() >= Player.BACKPACK_SIZE) throw new FullBackpackException();

        Card card = cardRepository.findCardById(cardId).orElseThrow(CardNotFoundException::new);

        if (!card.getCivilization().getId().equals(player.getCivilization().getId())) throw new CardNotFoundException();
        else if (card.getPlayer() != null) throw new PickedUpCardException();

        player.addCard(card);
        playerRepository.save(player);
        return card;
    }

    public void discardCard(Player player, String cardId) throws Exception {
        Card card = cardRepository.findCardById(cardId).orElseThrow(CardNotFoundException::new);
        if (card.getPlayer() == null || !card.getPlayer().getId().equals(player.getId()))
            throw new NotInYourBackpackException();
        player.removeCard(card);
        playerRepository.save(player);
    }

    public Card buyCard(Player player, String cardTypeId) throws Exception {
        CardType cardType = cardTypeRepository.findCardTypeById(cardTypeId).orElseThrow(CardTypeNotFoundException::new);

        if (!player.getCivilization().getAge().getId().equals(cardType.getAge().getId()))
            throw new NotCorrectAgeException();
        else if (player.getCivilization().getTreasury().getChivalry() < cardType.getChivalryCost())
            throw new NotEnoughResourcesException();

        Card card = Card.builder()
                .id(UUID.randomUUID().toString())
                .cardType(cardType)
                .civilization(player.getCivilization())
                .armory(player.getCivilization().getArmory())
                .level(0)
                .build();
        cardRepository.save(card);
        Treasury treasury = player.getCivilization().getTreasury();
        treasury.decreaseChivalry(cardType.getChivalryCost());
        treasuryRepository.save(treasury);
        player.getCivilization().getArmory().getCards().add(card);
        armoryRepository.save(player.getCivilization().getArmory());
        return card;
    }

    public void sellCard(Player player, String cardId) throws Exception {
        Card card = cardRepository.findCardById(cardId).orElseThrow(CardNotFoundException::new);
        if (card.getPlayer() != null) throw new PickedUpCardException();

        Treasury treasury = player.getCivilization().getTreasury();
        treasury.increaseChivalry(card.getCardType().getChivalryValue());
        treasuryRepository.save(treasury);

        Armory armory = player.getCivilization().getArmory();
        List<Card> collect = armory.getCards().stream().filter(c -> c.getId().equals(cardId)).collect(Collectors.toList());
        armory.getCards().removeAll(collect);
        armoryRepository.save(armory);
        cardRepository.delete(card);
    }

    public Set<Card> getPlayerCards(Player player) {
        return player.getCards();
    }
}
