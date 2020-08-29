package clash.back.domain.entity;

import clash.back.domain.entity.building.MapEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

    @OneToMany
    List<MapEntity> mapEntities;

    @OneToMany
    List<Player> players;

    @OneToOne
    World world;
}
