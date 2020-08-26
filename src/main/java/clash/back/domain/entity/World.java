package clash.back.domain.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class World {
    @Id
    String id;

    int turn;

    @OneToOne
    Map map;

    @OneToMany
    Set<Civilization> civilizations;
}
