package clash.back.repository;

import clash.back.domain.entity.Age;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgeRepository extends CrudRepository<Age, String> {
    boolean existsByName(String name);
    Optional<Age> findByOrderNo(int orderNo);

    Optional<Age> findByName(String name);
}
