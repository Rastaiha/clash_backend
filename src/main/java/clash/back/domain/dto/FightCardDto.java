package clash.back.domain.dto;

import clash.back.domain.entity.Card;
import lombok.Getter;

@Getter
public class FightCardDto implements IInputDto<Card> {

    String id;

    @Override
    public boolean isValid() {
        return !id.isEmpty();
    }

    @Override
    public Card fromDto() {
        return Card.builder().id(id).build();
    }
}
