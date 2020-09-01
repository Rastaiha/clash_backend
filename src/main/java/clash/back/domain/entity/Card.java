package clash.back.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


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
    int power;
    @ManyToOne(fetch = FetchType.EAGER)
    Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    Civilization civilization;

    @ManyToOne(fetch = FetchType.EAGER)
    Armory armory;

}
