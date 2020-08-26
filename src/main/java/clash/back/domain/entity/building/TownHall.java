package clash.back.domain.entity.building;

import clash.back.domain.entity.Civilization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.UUID;

@Data
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
