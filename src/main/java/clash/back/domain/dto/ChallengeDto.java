package clash.back.domain.dto;

import clash.back.domain.entity.Challenge;
import clash.back.domain.entity.ChallengeType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChallengeDto implements IOutputDto<Challenge> {
    String fileName;
    String jitsiLink;
    ChallengeType challengeType;

    @Override
    public IOutputDto<Challenge> toDto(Challenge challenge) {
        return ChallengeDto.builder()
                .fileName(challenge.getTemplate().getFileName())
                .jitsiLink(challenge.getId())
                .challengeType(challenge.getTemplate().getChallengeType()).build();
    }
}
