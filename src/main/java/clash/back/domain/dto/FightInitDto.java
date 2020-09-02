package clash.back.domain.dto;

import clash.back.domain.entity.Fight;
import clash.back.domain.entity.FightStage;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FightInitDto implements IOutputDto<Fight> {
    FighterDto host, guest;
    FightStage fightStage;

    @Override
    public IOutputDto<Fight> toDto(Fight fight) {
        return FightInitDto.builder().host((FighterDto) new FighterDto().toDto(fight.getHost()))
                .guest((FighterDto) new FighterDto().toDto(fight.getGuest())).fightStage(FightStage.INITIALIZING).build();
    }
}
