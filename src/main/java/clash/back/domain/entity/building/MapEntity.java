package clash.back.domain.entity.building;

import clash.back.domain.entity.Map;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MapEntity {
    @Id
    String id;
    protected int x, y;
    String rootId;
    transient String name;

    public Location getLocation() {
        return new Location(x, y);
    }

    public MapEntity buildMap(Map map) {
        this.map = map;
        return this;
    }

    public MapEntity buildLocation(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        return this;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Map map;
}
