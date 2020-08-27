package clash.back.handler;

import clash.back.domain.dto.MessageDto;
import clash.back.domain.entity.Fight;
import clash.back.domain.entity.PlayerStatus;

public class FightHandler extends DefaultHandler {
    final Fight fight;
    int round = 0;
    private static final int ROUNDS_COUNT = 5;
    private static final String CHOOSE_CARD_ALERT = "choose a card from your deck.";

    public FightHandler(Fight fight) {
        this.fight = fight;
        RELOAD_INTERVAL = 5000L;
    }

    @Override
    void handle() {
        chooseCard();
    }

    void chooseCard() {
        messageRouter.sendToSpecificPlayer(fight.getGuest(), new MessageDto().toDto(CHOOSE_CARD_ALERT));
        messageRouter.sendToSpecificPlayer(fight.getHost(), new MessageDto().toDto(CHOOSE_CARD_ALERT));
    }

    @Override
    public void init() {
        super.init();
        fight.getGuest().setStatus(PlayerStatus.FIGHTING);
        fight.getHost().setStatus(PlayerStatus.FIGHTING);
    }

}
