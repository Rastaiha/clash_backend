package clash.back.domain.dto;

import lombok.Getter;

@Getter
public class MapEntityInitializerDto implements IInputDto<String> {
    int width, height, count;
    String name;

    @Override
    public boolean isValid() {
        return !name.isEmpty();
    }

    @Override
    public String fromDto() {
        return name;
    }
}
