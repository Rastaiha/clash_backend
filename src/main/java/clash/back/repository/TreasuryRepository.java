package clash.back.repository;

import clash.back.domain.entity.Treasury;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreasuryRepository extends CrudRepository<Treasury, String> {
}
