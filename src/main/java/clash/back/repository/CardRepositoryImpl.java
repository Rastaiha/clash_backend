package clash.back.repository;

import clash.back.domain.entity.Civilization;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CardRepositoryImpl implements CardRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public void resetCardLevels(Civilization civilization) {
        entityManager.createQuery(
        "UPDATE Card " +
        "SET level = 0 " +
        "WHERE civilization_id = :civilizationId"
        ).setParameter("civilizationId", civilization.getId()).executeUpdate();

    }
}
