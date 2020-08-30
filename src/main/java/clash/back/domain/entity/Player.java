package clash.back.domain.entity;

import clash.back.domain.entity.building.Location;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.util.List;

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

    int x, y;
    int targetX, targetY;

    @Unique
    String username;

    String password;

    @ManyToOne
    Civilization civilization;

    @OneToMany
    List<Card> cards;

    @OneToOne
    Treasury treasury;

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
}
