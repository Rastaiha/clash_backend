package clash.back.domain.entity;

import clash.back.domain.entity.building.Location;
import clash.back.domain.entity.building.TownHall;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Civilization {
    static final int FIGHT_WIN_CHIVALRY_PRIZE = 300;
    static final int FIGHT_LOSE_CHIVALRY_PENALTY = 150;
    static final int FIGHT_WIN_XP = 500;
    static final int FIGHT_LOSE_XP = 50;
    static final int[] LEVELS = {100, 1000, 2000, 5000, 10000, 20000, 50000, 100000};
    static final double LEVEL_PRIZE_RATIO = 1.1;
    static final double EXPENSE_AGE_CONSTANT = 1.77;
    @Id
    String id;

    String name;
    int XP;
    int chivalry;
    int knowledge;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Player> players;

    @ManyToOne
    Age age;

    @OneToOne
    TownHall townHall;

    @OneToOne
    Armory armory;

    @ManyToOne
    World world;

    void init() {
    }

    public int getLevel() {
        return (int) (Math.log(XP / 1000) / Math.log(1.15));
    }

    public Location getLocation() {
        return new Location(townHall.getX(), townHall.getY());
    }

    public void increaseChivalry(int value) {
        chivalry += value;
    }

    public void decreaseChivalry(int value) {
        chivalry -= value;
    }

    public void increaseKnowledge(int value) {
        knowledge += value;
    }

    public void decreaseKnowledge(int value) {
        knowledge -= value;
    }

    public void addFightWinnerPrize() {
        increaseChivalry(age.adoptedIncome(FIGHT_WIN_CHIVALRY_PRIZE));
        ageAdoptedIncreaseXP(FIGHT_WIN_XP);
    }

    public void addFightLoserPrize() {
        decreaseChivalry(age.adoptedExpense(FIGHT_LOSE_CHIVALRY_PENALTY));
        ageAdoptedIncreaseXP(FIGHT_LOSE_XP);
    }

    public void ageAdoptedIncreaseXP(int value) {
        XP += age.adoptedXP(value);
    }
}
