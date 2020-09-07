package clash.back.service;

import clash.back.domain.entity.*;
import clash.back.exception.*;
import clash.back.repository.*;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArmoryService {
    //  TODO consider location
    // TODO: 31.08.20 restful api
    @Autowired
    CardRepository cardRepository;

    @Autowired
    CardTypeRepository cardTypeRepository;

    @Autowired
    CivilizationRepository civilizationRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ArmoryRepository armoryRepository;

    public Iterable<CardType> getCardTypes() {
        return cardTypeRepository.findAll();
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
        Civilization civilization = player.getCivilization();
        int chivalryCost = civilization.getAge().adoptedExpense(cardType.getChivalryCost()),
                knowledgeCost = civilization.getAge().adoptedExpense(cardType.getKnowledgeCost());
        if (civilization.getChivalry() < chivalryCost || civilization.getKnowledge() < knowledgeCost)
            throw new NotEnoughResourcesException();

        Armory armory = player.getCivilization().getArmory();
        Card card = Card.builder()
                .id(UUID.randomUUID().toString())
                .cardType(cardType)
                .civilization(player.getCivilization())
                .armory(armory)
                .level(0)
                .build();
        cardRepository.save(card);

        ArrayList<Card> cards = new ArrayList<>(Sets.newHashSet(armory.getCards()));
        cards.add(card);
        armory.setCards(cards);
        civilization.decreaseChivalry(chivalryCost);
        civilization.decreaseKnowledge(knowledgeCost);
        civilization.ageAdoptedIncreaseXP(cardType.getCreateXP());
        civilizationRepository.save(civilization);
        armoryRepository.save(armory);
        return card;
    }

    public void sellCard(Player player, String cardId) throws Exception {
        Card card = cardRepository.findCardById(cardId).orElseThrow(CardNotFoundException::new);
        if (card.getPlayer() != null) throw new PickedUpCardException();

        Civilization civilization = player.getCivilization();
        civilization.increaseChivalry(civilization.getAge().adoptedIncome(card.getCardType().getChivalryCost()));
        civilizationRepository.save(civilization);

        Armory armory = player.getCivilization().getArmory();
        List<Card> collect = armory.getCards().stream().filter(c -> c.getId().equals(cardId)).collect(Collectors.toList());
        armory.getCards().removeAll(collect);
        armory.setCards(new ArrayList<>(Sets.newHashSet(armory.getCards())));
        armoryRepository.save(armory);
        cardRepository.delete(card);
    }

    public Card upgradeCard(Player player, String cardId) throws Exception {
        Card card = cardRepository.findCardById(cardId).orElseThrow(CardNotFoundException::new);
        if (card.getPlayer() != null) throw new PickedUpCardException();

        int upgradeChivalryCost = card.getUpgradeChivalryCost();
        int upgradeKnowledgeCost = card.getUpgradeKnowledgeCost();

        Civilization civilization = player.getCivilization();
        if (upgradeChivalryCost > civilization.getChivalry() || upgradeKnowledgeCost > civilization.getKnowledge())
            throw new NotEnoughResourcesException();
        civilization.decreaseChivalry(upgradeChivalryCost);
        civilization.decreaseKnowledge(upgradeKnowledgeCost);
        civilization.ageAdoptedIncreaseXP(card.getUpgradeXP());
        civilizationRepository.save(civilization);

        card.upgrade();
        cardRepository.save(card);
        return card;
    }

    public Set<Card> getPlayerCards(Player player) {
        return player.getCards();
    }
}
