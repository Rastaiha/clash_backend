package clash.back.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<CardType> cardTypes;
}
