package clash.back.domain.dto;

import clash.back.domain.entity.building.TownHall;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TownHallDto implements IOutputDto<TownHall> {
    int x, y;

    @Override
    public IOutputDto<TownHall> toDto(TownHall townHall) {
        return TownHallDto.builder().x(townHall.getX()).y(townHall.getY()).build();
    }
}
