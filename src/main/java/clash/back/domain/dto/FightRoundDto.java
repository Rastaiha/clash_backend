package clash.back.domain.dto;

import clash.back.domain.entity.FightRound;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FightRoundDto implements IOutputDto<FightRound> {
    FighterDto winner;
    FighterDto loser;
    int round;
    Set<FightCardDto> cardsPlayed;

    @Override
    public IOutputDto<FightRound> toDto(FightRound fightRound) {
        return FightRoundDto.builder()
                .loser((FighterDto) new FighterDto().toDto(fightRound.getLoser()))
                .winner((FighterDto) new FighterDto().toDto(fightRound.getWinner()))
                .round(fightRound.getRound())
                .cardsPlayed(fightRound.getCardsPlayed().stream().map(card -> (FightCardDto) new FightCardDto().toDto(card)).collect(Collectors.toSet()))
                .build();
    }
}
