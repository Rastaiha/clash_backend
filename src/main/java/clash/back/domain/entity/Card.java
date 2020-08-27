package clash.back.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;


@Getter
@Setter
@Entity
public class Card {
    @Id
    String id;
    CardType cardType;
    int level;
    int cost;
    int upgradeCost;
}
