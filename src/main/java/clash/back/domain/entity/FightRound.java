package clash.back.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FightRound {
    Fighter winner;
    Fighter loser;
    int round;
    Card winnerCard;
    Card loserCard;
}
