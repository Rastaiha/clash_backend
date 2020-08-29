package clash.back.util.pathFinding;

import clash.back.domain.entity.building.Location;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Station implements GraphNode{

    private final String id = UUID.randomUUID().toString();
    private final Location location;

    public Station(Location location) {
        this.location = location;
    }

    public Station(int x, int y) {
        this.location = new Location(x, y);
    }

    @Override
    public String getId() {
        return this.id;
    }
}
