package clash.back.domain.dto;

import clash.back.domain.entity.Challenge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto implements IOutputDto<Challenge> {
    String id;
    String answer;
    String username;
    ChallengeDto question;


    @Override
    public IOutputDto<Challenge> toDto(Challenge challenge) {
        return AnswerDto.builder()
                .question((ChallengeDto) new ChallengeDto().toDto(challenge))
                .answer(challenge.getAnswer())
                .id(challenge.getId())
                .username(challenge.getPlayer().getUsername())
                .build();
    }
}
