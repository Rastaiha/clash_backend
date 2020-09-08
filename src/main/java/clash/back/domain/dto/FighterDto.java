package clash.back.domain.dto;

import clash.back.domain.entity.Fighter;
import clash.back.domain.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FighterDto implements IOutputDto<Fighter> {
    int roundsWon;
    String username;
    String name;

    @Override
    public IOutputDto<Fighter> toDto(Fighter fighter) {
        return FighterDto.builder().roundsWon(fighter.getRoundsWon()).username(fighter.getPlayer().getUsername())
                .name(fighter.getPlayer().getName()).build();
    }

    public IOutputDto<Fighter> toDto(Player player) {
        return FighterDto.builder().roundsWon(0).username(player.getUsername()).name(player.getName()).build();
    }
}
