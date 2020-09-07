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
    int chivalryCost, knowledgeCost, createXP, orderNo;
    int power;
}
