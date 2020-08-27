package clash.back.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Age {
    @Id
    String id;
    String name;
    int knowledgeCost;
    int chivalryCost;
}
