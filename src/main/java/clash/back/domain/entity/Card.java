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
    static final double SELL_VALUE_RATIO = 0.8;
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
        double basePower = civilization.getAge().adoptedCardPower(cardType.getPower());
        return (int) (basePower + (basePower * level / 5));
    }

    public void upgrade() {
        level += 1;
    }

    public int getUpgradeChivalryCost() {
        return civilization.getAge().adoptedExpense((int) (SELL_VALUE_RATIO * (cardType.getChivalryCost()) * Math.pow(1.5, level + 1)));
    }

    public int getUpgradeKnowledgeCost() {
        return civilization.getAge().adoptedExpense((int) (SELL_VALUE_RATIO * (cardType.getChivalryCost()) / 10 * Math.pow(1.5, level + 1)));
    }

    public int getUpgradeXP() {
        return cardType.getCreateXP() + (cardType.getCreateXP() * (level + 1) / 10);
    }
}
