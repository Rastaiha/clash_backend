package clash.back.domain.entity;

import clash.back.domain.entity.building.Location;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    public static final int BACKPACK_SIZE = 5;

    @Id
    String id;

    String name;

    int x, y;

    transient int targetX, targetY;

    boolean isMentor;

    boolean requestsUpgrade;

    Gender gender;

    @Unique
    String username;

    String password;

    long lastSeen;

    @ManyToOne
    Civilization civilization;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    Set<Card> cards;

    @OneToMany(mappedBy = "recipient", fetch = FetchType.EAGER)
    Set<Notification> notifications;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    Set<Challenge> challenges;

    transient PlayerStatus status = PlayerStatus.IDLE;

    public Location getLocation() {
        return new Location(x, y);
    }

    public Location getTargetLocation() {
        return new Location(targetX, targetY);
    }

    public void setLocation(Location location) {
        this.x = location.getX();
        this.y = location.getY();
    }

    public boolean isReady() {
        return this.getStatus().equals(PlayerStatus.WALKING) || this.getStatus().equals(PlayerStatus.IDLE);
    }

    public boolean isWalking() {
        return this.getStatus().equals(PlayerStatus.WALKING);
    }

    public boolean isNeighbourWith(Location location) {
        return Math.abs(this.getLocation().getX() - location.getX()) + Math.abs(this.getLocation().getY() - location.getY()) <= 1;
    }

    public void addCard(Card card) {
        cards.add(card);
        card.setPlayer(this);
    }

    public void removeCard(Card card) {
        cards = cards.stream().filter(card1 -> !card1.getId().equals(card.getId())).collect(Collectors.toSet());
        card.setPlayer(null);
    }
}
