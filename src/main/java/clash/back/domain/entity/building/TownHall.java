package clash.back.domain.entity.building;

import clash.back.domain.entity.Civilization;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class TownHall extends MapEntity {
    @OneToOne
    Civilization civilization;
}
