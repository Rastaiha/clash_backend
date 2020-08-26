package clash.back.domain.entity.building;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
@AllArgsConstructor
public class Motel extends MapEntity{
    public Motel(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = UUID.randomUUID().toString();
        name = "MOTEL";
    }
}
