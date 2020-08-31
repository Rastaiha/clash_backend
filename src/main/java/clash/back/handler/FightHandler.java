package clash.back.handler;

import clash.back.domain.dto.TimerDto;
import clash.back.domain.entity.Fight;
import clash.back.domain.entity.FightStage;
import clash.back.domain.entity.PlayerStatus;

import java.util.Date;

public class FightHandler extends DefaultHandler {
    final Fight fight;
    int round = 0;

    static {
        RELOAD_INTERVAL = 1000L;
    }

    private static final int ROUNDS_COUNT = 5;
    private static final String CHOOSE_CARD_ALERT = "choose a card from your deck.";

    int countDown = 10;
    FightStage fightStage;

    public FightHandler(Fight fight) {
        this.fight = fight;
    }

    @Override
    void handle() {
        switch (fightStage) {
            case STARTING:
                break;
            case WAITING_FOR_GUEST:
                break;
            case WAITING_FOR_HOST:
                break;
            case FIGHTING:
                break;
            case FINISHED:
                break;
        }
    }

    void sendChooseCardAlert() {
        if (countDown > 0) {
            messageRouter.sendToSpecificPlayer(fight.getGuest(), new TimerDto().toDto(countDown, CHOOSE_CARD_ALERT));
            messageRouter.sendToSpecificPlayer(fight.getHost(), new TimerDto().toDto(countDown, CHOOSE_CARD_ALERT));
        } else this.fightStage = FightStage.FIGHTING;
    }

    @Override
    public void init() {
        super.init();
        fight.getGuest().setStatus(PlayerStatus.FIGHTING);
        fight.getHost().setStatus(PlayerStatus.FIGHTING);
        this.fightStage = FightStage.STARTING;
        fight.setStartTime(new Date().getTime());
    }

}
