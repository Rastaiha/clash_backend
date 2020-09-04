package clash.back.domain.dto;

import clash.back.domain.entity.Challenge;

public class AnswerDto implements IOutputDto<Challenge> {
    String answer;
    String username;
    ChallengeDto question;


    @Override
    public IOutputDto<Challenge> toDto(Challenge challenge) {
        return null;
    }
}
