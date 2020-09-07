package clash.back.domain.dto;

import clash.back.domain.entity.Player;
import clash.back.domain.entity.building.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto implements IOutputDto<Player> {

    String username;
    List<CardDto> cards;
    Location location;

    @Override
    public IOutputDto<Player> toDto(Player player) {
        List<CardDto> collect = player.getCards().stream().map(card -> (CardDto) new CardDto().toDto(card)).collect(Collectors.toList());

        return PlayerDto.builder()
                .username(player.getUsername())
                .location(player.getLocation())
                .cards(collect)
                .build();
    }
}
