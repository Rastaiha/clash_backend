package clash.back.repository;

import clash.back.domain.entity.Armory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArmoryRepository extends CrudRepository<Armory, String> {
}
