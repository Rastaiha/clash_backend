package clash.back.domain.dto;

import clash.back.domain.entity.Map;
import lombok.Getter;

import java.util.List;

@Getter
public class MapInitializerDto implements IInputDto<Map> {
    int width, height;
    List<MapEntityInitializerDto> entities;

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public Map fromDto() {
        return null;
    }
}
