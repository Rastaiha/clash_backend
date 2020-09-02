package clash.back.domain.dto;

import clash.back.domain.entity.Fighter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FighterDto implements IOutputDto<Fighter> {
    int roundsWon;
    String username;

    @Override
    public IOutputDto<Fighter> toDto(Fighter fighter) {
        return FighterDto.builder().roundsWon(fighter.getRoundsWon()).username(fighter.getPlayer().getUsername()).build();
    }
}
