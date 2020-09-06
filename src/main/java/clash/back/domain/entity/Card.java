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
    static final double UPGRADE_COST_RATIO = 1.1;
    static final double UPGRADE_POWER_STEP = 0.1;
    @Id
    String id;
    @ManyToOne(fetch = FetchType.EAGER)
    CardType cardType;
    int level;
    @ManyToOne(fetch = FetchType.EAGER)
    Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    Civilization civilization;

    @ManyToOne(fetch = FetchType.EAGER)
    Armory armory;

    public int getPower() {
        return (int) (cardType.getPower() * (level * UPGRADE_POWER_STEP  + 1));
    }

    public int getUpgradeCost() {
        return (int) ((Math.pow(UPGRADE_COST_RATIO, level + 1) - Math.pow(UPGRADE_COST_RATIO, level)) * cardType.getChivalryCost());
    }

    public void upgrade() {
        level += 1;
    }
}
