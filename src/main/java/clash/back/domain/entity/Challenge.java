package clash.back.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Challenge {
    @Id
    String id;
    String question;
    ChallengeStatus status;

    @ManyToOne
    Player player;
}
