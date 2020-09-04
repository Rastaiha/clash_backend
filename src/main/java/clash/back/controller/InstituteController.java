package clash.back.controller;

import clash.back.domain.dto.AnswerDto;
import clash.back.domain.dto.ChallengeDto;
import clash.back.service.InstituteService;
import clash.back.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/institute")
public class InstituteController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    InstituteService instituteService;

    @GetMapping
    public ResponseEntity<ChallengeDto> getNewChallenge() {
        return ResponseEntity.ok((ChallengeDto) new ChallengeDto().toDto(instituteService.getNewChallenge(userDetailsService.getUser())));
    }

    @GetMapping("get_answers")
    public ResponseEntity<List<AnswerDto>> getAnswers() {
        return null;
    }
}
