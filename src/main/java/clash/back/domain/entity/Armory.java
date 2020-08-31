package clash.back.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Armory {

    @Id
    String id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Card> cards;

    @OneToOne
    Civilization civilization;
}
