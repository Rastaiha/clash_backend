package clash.back.domain.dto;

import clash.back.domain.entity.Player;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerMovementDto implements IOutputDto<Player> {
    String playerName, name;
    int x, y;

    @Override
    public IOutputDto<Player> toDto(Player player) {
        return PlayerMovementDto.builder().playerName(player.getUsername()).name(player.getName()).x(player.getX()).y(player.getY()).build();
    }
}
