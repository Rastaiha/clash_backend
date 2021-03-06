package clash.back.domain.entity;

import clash.back.exception.NoCardAvailableException;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

@Getter
public class Fighter {
    Player player;
    boolean isHost;
    Stack<Card> playedCards;
    Set<Card> inHandsCards;
    int roundsWon;

    public Fighter(Player player, boolean isHost) {
        this.player = player;
        this.isHost = isHost;
        playedCards = new Stack<>();
        inHandsCards = new HashSet<>(player.getCards());
        roundsWon = 0;
    }

    public Card playRandomCard() throws NoCardAvailableException {
        Card card = inHandsCards.stream().findAny().orElseThrow(NoCardAvailableException::new);
        playedCards.push(card);
        inHandsCards.remove(card);
        return card;
    }

    public void increaseWinningsCount() {
        this.roundsWon++;
    }

}
