package clash.back.domain.entity.building;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
public class Wall extends MapEntity {
    public Wall(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = UUID.randomUUID().toString();
    }

    public Wall(Location location, String rootId) {
        Wall wall = new Wall(location.getX(), location.getY());
        this.id = wall.getId();
        this.x = wall.getX();
        this.y = wall.getY();
        this.rootId = rootId;
    }
}
