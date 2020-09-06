package clash.back.domain.entity;

import clash.back.domain.entity.building.MapEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Map {
    @Id
    String id;

    int width, height;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<MapEntity> mapEntities;

    @OneToMany
    List<Player> players;

    @OneToOne
    World world;
}
