package clash.back.domain.dto;

import clash.back.domain.entity.FightRound;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FightRoundDto implements IOutputDto<FightRound> {
    FighterDto winner;
    FighterDto loser;
    int round;
    FightCardDto winnerCard;
    FightCardDto loserCard;

    @Override
    public IOutputDto<FightRound> toDto(FightRound fightRound) {
        return FightRoundDto.builder()
                .loser((FighterDto) new FighterDto().toDto(fightRound.getLoser()))
                .winner((FighterDto) new FighterDto().toDto(fightRound.getWinner()))
                .round(fightRound.getRound())
                .winnerCard((FightCardDto) new FightCardDto().toDto(fightRound.getWinnerCard()))
                .loserCard((FightCardDto) new FightCardDto().toDto(fightRound.getLoserCard()))
                .build();
    }
}
