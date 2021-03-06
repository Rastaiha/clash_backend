package clash.back.domain.entity;

import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Challenge {
    @Id
    String id;

    @ManyToOne
    ChallengeTemplate template;

    @Nullable
    String answer;

    ChallengeStatus status;
    ChallengeType type;
    @Nullable
    Boolean result;

    @ManyToOne
    Player player;
}
