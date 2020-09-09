package clash.back.util.pathFinding;

import clash.back.domain.entity.building.Location;
import clash.back.domain.entity.building.MapEntity;
import lombok.Getter;

import java.util.Optional;
import java.util.UUID;

@Getter
public class Station implements GraphNode{

    private final String id = UUID.randomUUID().toString();
    private final Location location;
    private final Optional<MapEntity> mapEntity;

    public Station(Location location, Optional<MapEntity> mapEntity) {
        this.location = location;
        this.mapEntity = mapEntity;
    }

    public Station(int x, int y, Optional<MapEntity> mapEntity) {
        this.mapEntity = mapEntity;
        this.location = new Location(x, y);
    }

    @Override
    public String getId() {
        return this.id;
    }
}
