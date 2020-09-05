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

    public Location getLocation() {
        return new Location(x, y);
    }

    public MapEntity buildMap(Map map) {
        this.map = map;
        return this;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Map map;
}
