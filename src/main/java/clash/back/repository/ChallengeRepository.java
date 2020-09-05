package clash.back.repository;

import clash.back.domain.entity.Challenge;
import clash.back.domain.entity.ChallengeStatus;
import clash.back.domain.entity.ChallengeType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends CrudRepository<Challenge, String> {
    List<Challenge> findByStatusAndType(ChallengeStatus status, ChallengeType type);
}
