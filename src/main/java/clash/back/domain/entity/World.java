package clash.back.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class World {
    @Id
    String id;

    int turn;

    @OneToOne
    Map map;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Civilization> civilizations;
}
