package clash.back.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeTemplate {
    @Id
    String id;
    String fileName;
    ChallengeType challengeType;
}
