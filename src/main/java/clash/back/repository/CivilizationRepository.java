package clash.back.repository;

import clash.back.domain.entity.Civilization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CivilizationRepository extends CrudRepository<Civilization, String> {
    boolean existsByName(String name);
}
