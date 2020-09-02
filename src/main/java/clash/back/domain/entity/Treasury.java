package clash.back.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
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

    public void increaseKnowledge(int value) {
        knowledge += value;
    }

    public void decreaseKnowledge(int value) {
        knowledge -= value;
    }

    public void increaseTreasury(Treasury treasury) {
        increaseChivalry(treasury.getChivalry());
        increaseKnowledge(treasury.getKnowledge());
    }

    public void unfillTreasury() {
        chivalry = 0;
        knowledge = 0;
    }
}
