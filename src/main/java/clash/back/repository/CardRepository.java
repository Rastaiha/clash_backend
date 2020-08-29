package clash.back.repository;

import clash.back.domain.entity.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends CrudRepository<Card,String> {
    Optional<Card> findCardById(String s);
}
