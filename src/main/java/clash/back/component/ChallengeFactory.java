package clash.back.component;

import clash.back.domain.entity.Challenge;
import clash.back.domain.entity.ChallengeStatus;
import clash.back.domain.entity.ChallengeTemplate;
import clash.back.domain.entity.Player;
import clash.back.exception.NoChallengeTemplateFoundException;
import clash.back.repository.ChallengeTemplateRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChallengeFactory {

    private static final ChallengeFactory INSTANCE = new ChallengeFactory();
    private static ChallengeTemplateRepository challengeTemplateRepository;


    private ChallengeFactory() {
    }

    public static ChallengeFactory getInstance() {
        return INSTANCE;
    }

    public static void setChallengeTemplateRepository(ChallengeTemplateRepository challengeTemplateRepository) {
        ChallengeFactory.challengeTemplateRepository = challengeTemplateRepository;
    }

    public Challenge buildChallenge(Player player) throws NoChallengeTemplateFoundException {
        ChallengeTemplate template = challengeTemplateRepository.getRandomRow().orElseThrow(NoChallengeTemplateFoundException::new);
        return Challenge.builder()
                .id(UUID.randomUUID().toString()).player(player)
                .template(template)
                .type(template.getChallengeType())
                .status(ChallengeStatus.IN_BACKPACK)
                .build();
    }
}
