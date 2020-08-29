package clash.back.domain.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Treasury {
    @Id
    String id;

    public Treasury() {
        this.id = UUID.randomUUID().toString();
    }

    int chivalry;
    int knowledge;

    public void increaseChivalry(int value) {
        chivalry += value;
    }

    public void decreaseChivalry(int value) {
        chivalry -= value;
    }
}
