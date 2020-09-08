package clash.back.repository;

import clash.back.domain.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends CrudRepository<Player, String> {
    Optional<Player> findPlayerById(String playerId);

    Optional<Player> findPlayerByUsernameIgnoreCase(String username);
}
