package clash.back.domain.entity.building;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
@AllArgsConstructor
public class Motel extends MapEntity {
    public Motel(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = UUID.randomUUID().toString();
    }

    public Motel(Location location) {
        Motel motel = new Motel(location.getX(), location.getY());
        this.x = motel.getX();
        this.y = motel.getY();
        this.id = motel.getId();
    }
}
