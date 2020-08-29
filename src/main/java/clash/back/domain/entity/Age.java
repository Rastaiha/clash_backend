package clash.back.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Age {
    @Id
    String id;
    String name;
    int knowledgeCost;
    int chivalryCost;
    @OneToMany
    List<CardType> cardTypes;
}
