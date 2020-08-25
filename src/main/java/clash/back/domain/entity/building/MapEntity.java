package clash.back.domain.entity.building;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Data
@Entity
public abstract class MapEntity {
    @Id
    UUID id;
    String name;

    @OneToOne
    Location location;
}
