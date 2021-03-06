package clash.back.domain.dto;

import clash.back.domain.entity.Fight;
import clash.back.domain.entity.FightStage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FightDto implements IOutputDto<Fight> {
    FightStage fightStage;
    int roundsPlayed;
    Set<FightRoundDto> fightRounds;

    @Override
    public IOutputDto<Fight> toDto(Fight fight) {
        return FightDto.builder().roundsPlayed(fight.getRounds().size()).fightRounds(fight.getRounds().stream()
                .map(fightRound -> (FightRoundDto) new FightRoundDto().toDto(fightRound)).collect(Collectors.toSet()))
                .fightStage(FightStage.FIGHTING)
                .build();
    }
}
