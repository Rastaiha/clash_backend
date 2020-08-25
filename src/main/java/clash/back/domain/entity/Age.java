package clash.back.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class Age {
    @Id
    UUID id;
    String name;
    int knowledgeCost;
    int chivalryCost;
}
