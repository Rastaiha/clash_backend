package clash.back.handler;

import clash.back.domain.dto.FightDto;
import clash.back.domain.dto.FightInitDto;
import clash.back.domain.dto.FightTimerDto;
import clash.back.domain.dto.MessageDto;
import clash.back.domain.entity.*;
import clash.back.exception.NoCardAvailableException;
import clash.back.util.Settings;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class FightHandler extends DefaultHandler {

    private static final int DEFAULT_COUNT_DOWN = 10;
    private static final int ROUNDS_COUNT = 5;
    private static final String CHOOSE_CARD_ALERT = "choose a card from your deck.";
    private static final String WON_ALERT = "Congratulation! You won the fight!";
    private static final String LOST_ALERT = "Sorry! You lost!";
    private static final String BE_READY_ALERT = "the fight will start soon!";

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
            case STARTING:
                start();
                countDown--;
            case WAITING:
                sendChooseCardAlert();
                countDown--;
                break;
            case FIGHTING:
                countDown = DEFAULT_COUNT_DOWN;
                fight();
                break;
            case FINISHED:
                finish();
                break;
        }
    }

    private void start() {
        if (countDown > 0)
            Arrays.stream(fighters).forEach(fighter -> messageRouter
                    .sendToSpecificPlayer(fighter.getPlayer(), new FightTimerDto().toDto(countDown, BE_READY_ALERT, FightStage.STARTING),
                            Settings.WS_FIGHT_DEST));

        else {
            this.fightStage = FightStage.WAITING;
            countDown = DEFAULT_COUNT_DOWN;
        }
    }

    private void fight() {
        Arrays.stream(fighters).filter(fighter -> fighter.getPlayedCards().size() < round)
                .forEach(fighter -> {
                    try {
                        currentRoundsDeck.add(fighter.playRandomCard());
                    } catch (NoCardAvailableException e) {
                        this.fightStage = FightStage.FINISHED;
                    }
                });
        if (currentRoundsDeck.size() < 2)
            return;

        ArrayList<Card> cards = new ArrayList<>(currentRoundsDeck);
        Card first = cards.get(0);
        Card sec = cards.get(1);
        Card winner = first.getPower() > sec.getPower() ? first : sec; //TODO: handle equal case
        Card loser = first.getPower() > sec.getPower() ? sec : first; //TODO: handle equal case
        Arrays.stream(fighters).filter(fighter -> fighter.getPlayer().getId().equals(winner.getPlayer().getId()))
                .forEach(Fighter::increaseWinningsCount);

        fight.getRounds().push(
                FightRound.builder()
                        .loser(Arrays.stream(fighters)
                                .filter(fighter -> !fighter.getPlayer().getId().equals(winner.getPlayer().getId())).findAny().get())
                        .winner(Arrays.stream(fighters).filter(fighter -> fighter.getPlayer().getId().equals(winner.getPlayer().getId())).findAny().get())
                        .winnerCard(winner)
                        .loserCard(loser)
                        .round(round)
                        .build());

        Arrays.stream(fighters).forEach(fighter -> messageRouter
                .sendToSpecificPlayer(fighter.getPlayer(), new FightDto().toDto(fight), Settings.WS_FIGHT_DEST));

        round++;
        countDown = DEFAULT_COUNT_DOWN;
        currentRoundsDeck.clear();
        fightStage = round <= ROUNDS_COUNT ? FightStage.WAITING : FightStage.FINISHED;
    }

    void sendChooseCardAlert() {
        if (countDown > 0)
            Arrays.stream(fighters).forEach(fighter -> messageRouter
                    .sendToSpecificPlayer(fighter.getPlayer(), new FightTimerDto().toDto(countDown, CHOOSE_CARD_ALERT, FightStage.WAITING), Settings.WS_FIGHT_DEST));

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
                    fighter.getInHandsCards().remove(card);
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

        this.fightStage = FightStage.STARTING;
        fight.setStartTime(new Date().getTime());
        fight.setRounds(new Stack<>());

        Arrays.stream(fighters).forEach(fighter -> messageRouter
                .sendToSpecificPlayer(fighter.getPlayer(), new FightInitDto().toDto(fight), Settings.WS_FIGHT_DEST));

        logger.info("Fight initiated, " + "host: " + fight.getHost().getUsername() + " guest: " + fight.getGuest().getUsername());
        round = 1;
    }

    void finish() {
//        Arrays.stream(fighters).forEach(fighter -> fighter.getPlayer().setStatus(PlayerStatus.RESTING));//todo : uncomment for deploy
        fight.setWinner(fighters[0].getRoundsWon() > fighters[1].getRoundsWon() ? fighters[0].getPlayer() : fighters[1].getPlayer());
        fight.setLoser(fighters[0].getRoundsWon() > fighters[1].getRoundsWon() ? fighters[1].getPlayer() : fighters[0].getPlayer());

        messageRouter.sendToSpecificPlayer(fight.getWinner(), new MessageDto().toDto(WON_ALERT), Settings.WS_FIGHT_DEST);
        messageRouter.sendToSpecificPlayer(fight.getLoser(), new MessageDto().toDto(LOST_ALERT), Settings.WS_FIGHT_DEST);

        logger.info("Fight finished, winner: " + fight.getWinner().getUsername() + " loser: " + fight.getLoser().getUsername());
    }
}
