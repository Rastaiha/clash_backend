package clash.back.domain.entity.building;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class Location {
    @Id
    UUID id;

    int x, y;
}
