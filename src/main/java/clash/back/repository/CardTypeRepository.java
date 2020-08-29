package clash.back.repository;

import clash.back.domain.entity.CardType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CardTypeRepository extends CrudRepository<CardType, String> {
    Optional<CardType> findCardTypeById(String s);
}
