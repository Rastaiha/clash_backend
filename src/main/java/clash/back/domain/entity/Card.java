package clash.back.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;


@Data
@Entity
public class Card {
    @Id
    String id;
    CardType cardType;
    int level;
    int cost;
    int upgradeCost;
}
