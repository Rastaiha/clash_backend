package clash.back.domain.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Stack;

@Builder
@Getter
@Setter
public class Fight {
    Player host, guest, winner, loser;
    Stack<FightRound> rounds;
    long startTime;
}
