package clash.back.repository;

import clash.back.domain.entity.building.MapEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapEntityRepository extends CrudRepository<MapEntity, String> {
    List<MapEntity> findByMapId(String mapId);
}
