package clash.back.repository;

import clash.back.domain.entity.Civilization;

public interface CardRepositoryCustom {
    void resetCardLevels(Civilization civilization);
}
