package clash.back.domain.entity;

import clash.back.domain.entity.building.Location;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Player {
    @Id
    UUID id;

    String name;

    @ManyToOne
    Civilization civilization;

    @OneToMany
    List<Card> cards;

    @OneToOne
    Treasury treasury;

    @OneToOne
    Location location;

}
