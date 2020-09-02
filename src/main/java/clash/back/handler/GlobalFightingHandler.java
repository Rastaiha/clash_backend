package clash.back.handler;

import clash.back.domain.entity.Card;
import clash.back.domain.entity.Fight;
import clash.back.domain.entity.FightStage;
import clash.back.domain.entity.Player;
import clash.back.exception.FighterNotAvailableException;
import clash.back.service.GameService;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
public class GlobalFightingHandler extends DefaultHandler {

    static {
        RELOAD_INTERVAL = 1000L;
    }

    Set<FightHandler> fightHandlers;
    GameService gameService;

    @Override
    void handle() {
        fightHandlers.removeIf(fightHandler -> fightHandler.getFightStage().equals(FightStage.FINALIZED));
        fightHandlers.forEach(FightHandler::handle);
        fightHandlers.stream().filter(fightHandler -> fightHandler.fightStage.equals(FightStage.FINISHED))
                .forEach(finished -> {
                    finished.finish();
                    gameService.finalizeFight(finished.getFight());
                    finished.setFightStage(FightStage.FINALIZED);
                });
    }

    @Override
    public void init() {
        super.init();
        fightHandlers = new HashSet<>();
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
