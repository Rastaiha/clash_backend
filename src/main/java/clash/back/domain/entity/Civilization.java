package clash.back.domain.entity;

import clash.back.domain.entity.building.Location;
import clash.back.domain.entity.building.TownHall;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Civilization {
    @Id
    String id;

    String name;
    int xp, level;

    private int x, y;

    @OneToMany
    Set<Player> players;

    @OneToOne
    Age age;

    @OneToOne
    Treasury treasury;

    @OneToOne
    TownHall townHall;

    void init() {
    }

    public Location getLocation(){
        return new Location(x,y);
    }
}
