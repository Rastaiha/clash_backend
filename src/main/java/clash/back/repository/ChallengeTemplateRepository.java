package clash.back.repository;

import clash.back.domain.entity.ChallengeTemplate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeTemplateRepository extends CrudRepository<ChallengeTemplate, String> {
    @Query(value = "SELECT * FROM challenge_template ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<ChallengeTemplate> getRandomRow();

    Optional<ChallengeTemplate> findByFileName(String fileName);
}
