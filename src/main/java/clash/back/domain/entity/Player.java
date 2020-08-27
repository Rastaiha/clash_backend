package clash.back.domain.entity;

import clash.back.domain.entity.building.Location;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    String id;

    int x,y;

    @Unique
    String username;

    @ManyToOne
    Civilization civilization;

    @OneToMany
    List<Card> cards;

    @OneToOne
    Treasury treasury;

    transient PlayerStatus status = PlayerStatus.IDLE;

    public Location getLocation(){
        return new Location(x,y);
    }

    public void setLocation(Location location) {
        this.x = location.getX();
        this.y = location.getY();
    }

    public boolean isReady() {
        return this.getStatus().equals(PlayerStatus.WALKING) || this.getStatus().equals(PlayerStatus.IDLE);
    }

}
