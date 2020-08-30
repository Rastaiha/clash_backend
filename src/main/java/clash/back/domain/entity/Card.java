package clash.back.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;


@Getter
@Setter
@Builder(toBuilder = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    String id;
    @ManyToOne(fetch = FetchType.EAGER)
    CardType cardType;
    int level;
    @ManyToOne(fetch = FetchType.EAGER)
    Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    Civilization civilization;

}
