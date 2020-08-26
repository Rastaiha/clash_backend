package clash.back.domain.entity.building;

import clash.back.domain.entity.Civilization;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TownHall extends MapEntity {
    @OneToOne
    Civilization civilization;

    public TownHall(int x, int y) {
        name = "TOWNHALL";
        this.x = x;
        this.y = y;
        this.id = UUID.randomUUID().toString();
    }
}
