package clash.back.service;

import clash.back.component.ChallengeFactory;
import clash.back.domain.dto.FileDto;
import clash.back.domain.dto.MarkDto;
import clash.back.domain.entity.Challenge;
import clash.back.domain.entity.ChallengeStatus;
import clash.back.domain.entity.ChallengeType;
import clash.back.domain.entity.Player;
import clash.back.exception.ChallengeNotFoundException;
import clash.back.exception.NoChallengeTemplateFoundException;
import clash.back.repository.ChallengeRepository;
import clash.back.repository.ChallengeTemplateRepository;
import clash.back.repository.PlayerRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
public class InstituteService {
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ChallengeRepository challengeRepository;

    @Autowired
    ChallengeTemplateRepository challengeTemplateRepository;


    public Challenge getNewChallenge(Player player) throws NoChallengeTemplateFoundException {
        Challenge challenge = ChallengeFactory.getInstance().buildChallenge(player);
        player.getChallenges().add(challenge);
        challengeRepository.save(challenge);
        playerRepository.save(player);
        return challenge;
    }

    public List<Challenge> getAnswers(ChallengeType challengeType) {
        return challengeRepository.findByStatusAndType(ChallengeStatus.SOLVED, challengeType);
    }

    public void submitAnswer(String challengeId, FileDto fileDto, Player user) throws ChallengeNotFoundException {
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(ChallengeNotFoundException::new);
        if (challenge.getPlayer().getId().equals(user.getId())) {
            challenge.setAnswer(fileDto.fromDto());
            challenge.setStatus(ChallengeStatus.SOLVED);
        } else throw new ChallengeNotFoundException();
        challengeRepository.save(challenge);
    }

    public void score(String challengeId, MarkDto markDto) throws ChallengeNotFoundException {
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(ChallengeNotFoundException::new);
        challengeRepository.save(challenge.toBuilder().result(markDto.fromDto()).status(ChallengeStatus.MARKED).build());
    }

    public Challenge getChallengeById(String challengeId) throws ChallengeNotFoundException {
        return challengeRepository.findById(challengeId).orElseThrow(ChallengeNotFoundException::new);
    }
}
