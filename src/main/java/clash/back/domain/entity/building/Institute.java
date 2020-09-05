package clash.back.domain.entity.building;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
@AllArgsConstructor
public class Institute extends MapEntity {

    public Institute(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = UUID.randomUUID().toString();
    }

    public Institute(Location location) {
        Institute institute = new Institute(location.getX(), location.getY());
        this.x = institute.getX();
        this.y = institute.getY();
        this.id = institute.getId();
    }
}
