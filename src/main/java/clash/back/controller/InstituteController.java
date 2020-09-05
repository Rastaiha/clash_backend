package clash.back.controller;

import clash.back.component.ChallengeFactory;
import clash.back.domain.dto.AnswerDto;
import clash.back.domain.dto.ChallengeDto;
import clash.back.domain.entity.Challenge;
import clash.back.domain.entity.ChallengeType;
import clash.back.domain.entity.PlayerStatus;
import clash.back.exception.ChallengeTypeNotFoundException;
import clash.back.exception.NoChallengeTemplateFoundException;
import clash.back.exception.NotCorrectPlaceException;
import clash.back.service.InstituteService;
import clash.back.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/institute")
public class InstituteController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    InstituteService instituteService;


    public void init() {
        ChallengeFactory.setChallengeTemplateRepository(instituteService.getChallengeTemplateRepository());
    }

    @GetMapping
    public ResponseEntity<ChallengeDto> getNewChallenge() throws NoChallengeTemplateFoundException, NotCorrectPlaceException {
        if (userDetailsService.getUser().getStatus().equals(PlayerStatus.IN_INSTITUTE))
            return ResponseEntity.ok((ChallengeDto) new ChallengeDto().toDto(instituteService.getNewChallenge(userDetailsService.getUser())));
        throw new NotCorrectPlaceException();
    }

    @GetMapping("/answers/{category}")
    public ResponseEntity<List<AnswerDto>> getAnswers(@PathVariable String category) throws ChallengeTypeNotFoundException {
        List<Challenge> answers = instituteService.getAnswers(Arrays.stream(ChallengeType.values())
                .filter(challengeType -> challengeType.toString().equalsIgnoreCase(category))
                .findAny().orElseThrow(ChallengeTypeNotFoundException::new));

        return ResponseEntity.ok(answers.stream().map(challenge -> ((AnswerDto) new AnswerDto().toDto(challenge))).collect(Collectors.toList()));
    }
}
