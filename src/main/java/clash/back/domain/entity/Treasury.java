package clash.back.domain.entity;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class Treasury {
    @Id
    String id;

    public Treasury() {
        this.id = UUID.randomUUID().toString();
    }

    int chivalry;
    int knowledge;
}
