package clash.back.handler;

import clash.back.domain.dto.TimerDto;
import clash.back.domain.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class FightHandler extends DefaultHandler {

    private static final int DEFAULT_COUNT_DOWN = 10;
    private static final int ROUNDS_COUNT = 5;
    private static final String CHOOSE_CARD_ALERT = "choose a card from your deck.";

    final Fight fight;

    int round = 0;
    int countDown = DEFAULT_COUNT_DOWN;
    Fighter[] fighters;
    Set<Card> currentRoundsDeck = new HashSet<>();

    FightStage fightStage;


    public FightHandler(Fight fight) {
        this.fight = fight;
    }

    @Override
    void handle() {
        switch (fightStage) {
            case WAITING:
                sendChooseCardAlert();
                countDown--;
                break;
            case FIGHTING:
                countDown = DEFAULT_COUNT_DOWN;
                fight();
                break;
            case FINISHED:
                System.out.println("finished");
                break;
        }
    }

    private void fight() {
        Arrays.stream(fighters).filter(fighter -> fighter.getPlayedCards().size() < round)
                .forEach(fighter -> currentRoundsDeck.add(fighter.playRandomCard()));

        ArrayList<Card> cards = new ArrayList<>(currentRoundsDeck);
        Card first = cards.get(0);
        Card sec = cards.get(1);
        Card winner = first.getPower() > sec.getPower() ? first : sec;
        Arrays.stream(fighters).filter(fighter -> fighter.getPlayer().getId().equals(winner.getPlayer().getId()))
                .forEach(Fighter::increaseWinningsCount);

        round++;
        fightStage = round < ROUNDS_COUNT ? FightStage.WAITING : FightStage.FINISHED;
    }

    void sendChooseCardAlert() {
        if (countDown > 0)
            Arrays.stream(fighters).forEach(fighter -> messageRouter
                    .sendToSpecificPlayer(fighter.getPlayer(), new TimerDto().toDto(countDown, CHOOSE_CARD_ALERT)));

        else this.fightStage = FightStage.FIGHTING;
    }

    boolean hasPlayer(Player player) {
        return this.fight.getHost().getId().equals(player.getId()) || this.fight.getGuest().getId().equals(player.getId());
    }

    void putCard(Card card, Player player) {
        Arrays.stream(fighters).filter(fighter -> fighter.getPlayer().getId().equals(player.getId()))
                .filter(fighter -> fighter.getPlayedCards().size() == round)
                .findAny()
                .ifPresent(fighter -> {
                    fighter.getPlayedCards().push(card);
                    currentRoundsDeck.add(card);
                });

        fightStage = currentRoundsDeck.size() == 2 ? FightStage.FIGHTING : FightStage.WAITING;
    }

    @Override
    public void init() {
        fight.getGuest().setStatus(PlayerStatus.FIGHTING);
        fight.getHost().setStatus(PlayerStatus.FIGHTING);

        fighters = new Fighter[2];
        fighters[0] = new Fighter(fight.getHost(), true);
        fighters[1] = new Fighter(fight.getGuest(), false);

        this.fightStage = FightStage.WAITING;
        fight.setStartTime(new Date().getTime());
    }
}
