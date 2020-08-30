package clash.back.domain.dto;

import clash.back.domain.entity.Map;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapDto implements IOutputDto<Map> {
    int height, width;
    List<MapEntityDto> mapEntities;

    @Override
    public IOutputDto<Map> toDto(Map map) {
        return MapDto.builder().height(map.getHeight()).width(map.getWidth())
                .mapEntities(map.getMapEntities().stream().map(mapEntity -> (MapEntityDto) new MapEntityDto().toDto(mapEntity))
                        .collect(Collectors.toList())).build();
    }
}
