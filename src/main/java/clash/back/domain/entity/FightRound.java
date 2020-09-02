package clash.back.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class FightRound {
    Fighter winner;
    Fighter loser;
    int round;
    Set<Card> cardsPlayed;
}
