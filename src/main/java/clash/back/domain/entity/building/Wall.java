package clash.back.domain.entity.building;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
public class Wall extends MapEntity {
    public Wall(int x, int y) {
        name = "WALL";
        this.x = x;
        this.y = y;
        this.id = UUID.randomUUID().toString();
    }
}
