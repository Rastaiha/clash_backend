package clash.back.handler;

import clash.back.domain.entity.Card;
import clash.back.domain.entity.Fight;
import clash.back.domain.entity.FightStage;
import clash.back.domain.entity.Player;
import clash.back.exception.FighterNotAvailableException;

import java.util.Set;

public class GlobalFightingHandler extends DefaultHandler {

    static {
        RELOAD_INTERVAL = 1000L;
    }

    Set<FightHandler> fightHandlers;

    @Override
    void handle() {
        fightHandlers.forEach(FightHandler::handle);
    }

    public void putCard(Card card, Player player) throws FighterNotAvailableException {
        fightHandlers.stream().filter(fightHandler -> fightHandler.hasPlayer(player)).findAny()
                .orElseThrow(FighterNotAvailableException::new).putCard(card, player);
    }

    public void startNewFight(Player host, Player guest) throws FighterNotAvailableException {
        if (isFighting(host) || isFighting(guest))
            throw new FighterNotAvailableException();

        FightHandler fightHandler = new FightHandler(Fight.builder().host(host).guest(guest).build());
        fightHandler.init();
        fightHandlers.add(fightHandler);
    }

    boolean isFighting(Player player) {
        return fightHandlers.stream().filter(fightHandler -> !fightHandler.fightStage.equals(FightStage.FINISHED))
                .anyMatch(fightHandler -> fightHandler.hasPlayer(player));
    }
}
