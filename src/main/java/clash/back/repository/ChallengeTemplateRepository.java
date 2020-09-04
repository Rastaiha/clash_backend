package clash.back.repository;

import clash.back.domain.entity.ChallengeTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeTemplateRepository extends CrudRepository<ChallengeTemplate, String> {
}
