package clash.back.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
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
