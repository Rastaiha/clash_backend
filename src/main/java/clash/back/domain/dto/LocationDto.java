package clash.back.domain.dto;

import clash.back.domain.entity.building.Location;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto implements IInputDto<Location>{
    int x, y;

    @Override
    public boolean isValid() {
        return x >= 0 && y >= 0;
    }

    @Override
    public Location fromDto() {
        return new Location(x,y);
    }
}
