package clash.back.domain.entity;

import clash.back.domain.entity.building.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
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

    public Location getLocation(){
        return new Location(x,y);
    }


}
