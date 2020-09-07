package clash.back.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Builder(toBuilder = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Age {
    static final double EXPENSE_CONSTANT = 1.77;
    @Id
    String id;
    String name;
    int knowledgeCost;
    int chivalryCost;
    int upgradeXP;
    int orderNo;

    public int adoptedExpense(int value) {
        return (int) (value * Math.pow(EXPENSE_CONSTANT, orderNo - 1));
    }

    public int adoptedIncome(int value) {
        return value * orderNo;
    }

    public int adoptedXP(int XP) {
        return (int) (XP * (0.5 + orderNo * 0.5));
    }

    public int adoptedCardPower(int power) {
        return (int) (power * (0.5 + orderNo * 0.5));
    }
}
