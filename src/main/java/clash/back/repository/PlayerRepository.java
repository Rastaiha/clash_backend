package clash.back.repository;

import clash.back.domain.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerRepository extends CrudRepository<Player, String> {
    Optional<Player> findPlayerById(UUID playerId);
    Optional<Player> findPlayerByUsername(String username);
}
