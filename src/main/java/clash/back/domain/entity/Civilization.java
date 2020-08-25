package clash.back.domain.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Civilization {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Type(type = "uuid-char")
    UUID id;

    String name;
    int xp, level;

    @OneToMany
    Set<Player> players;

    @OneToOne
    Age age;

    @OneToOne
    Treasury treasury;
}
