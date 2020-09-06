package clash.back.domain.entity.building;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
@AllArgsConstructor
public class Tree extends MapEntity {
    public Tree(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = UUID.randomUUID().toString();
    }

    public Tree(Location location, String rootId) {
        Tree tree = new Tree(location.getX(), location.getY());
        this.x = tree.getX();
        this.y = tree.getY();
        this.id = tree.getId();
        this.rootId = rootId;
    }
}
