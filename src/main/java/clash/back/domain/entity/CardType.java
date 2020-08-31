package clash.back.domain.entity;

import lombok.*;

import javax.persistence.*;

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
    @ManyToOne
    Age age;
}
