package clash.back.domain.entity;

import clash.back.domain.entity.building.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    UUID id;

    @Unique
    String username;

    @ManyToOne
    Civilization civilization;

    @OneToMany
    List<Card> cards;

    @OneToOne
    Treasury treasury;

    @OneToOne
    Location location;

}
