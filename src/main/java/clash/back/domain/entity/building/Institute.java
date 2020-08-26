package clash.back.domain.entity.building;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
@AllArgsConstructor
public class Institute extends MapEntity {
    public Institute(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = UUID.randomUUID().toString();
        name = "INSTITUE";
    }
}
