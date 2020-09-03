package clash.back.service;

import clash.back.domain.entity.Challenge;
import clash.back.domain.entity.Player;
import clash.back.repository.ChallengeRepository;
import clash.back.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstituteService {
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ChallengeRepository challengeRepository;


    public Challenge getNewChallenge(Player player) {
        return null;
    }
}
