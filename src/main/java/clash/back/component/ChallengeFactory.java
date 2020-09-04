package clash.back.component;

import clash.back.domain.entity.Challenge;
import clash.back.domain.entity.ChallengeStatus;
import clash.back.domain.entity.Player;
import clash.back.repository.ChallengeRepository;
import clash.back.repository.ChallengeTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChallengeFactory {

    @Autowired
    static ChallengeRepository challengeRepository;

    @Autowired
    static ChallengeTemplateRepository challengeTemplateRepository;

    public static Challenge buildChallenge(Player player) {
        return Challenge.builder()
                .id(UUID.randomUUID().toString()).player(player)
                .template(challengeTemplateRepository.findAll().iterator().next())
                .status(ChallengeStatus.IN_BACKPACK)
                .build();
    }
}
