package clash.back.domain.entity;

import clash.back.domain.entity.building.Location;
import clash.back.domain.entity.building.TownHall;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Civilization {
    @Id
    String id;

    String name;
    int xp, level;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Player> players;

    @ManyToOne
    Age age;

    @OneToOne
    Treasury treasury;

    @OneToOne
    TownHall townHall;

    @ManyToOne
    World world;

    void init() {
    }

    public Location getLocation(){
        return new Location(townHall.getX(), townHall.getY());
    }
}
