package clash.back.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardType {
    @Id
    String id;
    String name;
    int chivalryCost, chivalryValue, orderNo;
    int power;
    @ManyToOne
    Age age;
}
