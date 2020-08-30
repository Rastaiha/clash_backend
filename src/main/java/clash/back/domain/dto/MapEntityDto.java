package clash.back.domain.dto;

import clash.back.domain.entity.building.MapEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapEntityDto implements IOutputDto<MapEntity> {
    int x, y;
    String type;

    @Override
    public IOutputDto<MapEntity> toDto(MapEntity mapEntity) {
        return MapEntityDto.builder().x(mapEntity.getX()).y(mapEntity.getY()).type(mapEntity.getName()).build();
    }
}
