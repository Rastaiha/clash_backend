package clash.back.domain.dto;

import clash.back.domain.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FightCardDto implements IInputDto<Card>, IOutputDto<Card> {
    CardTypeDto cardType;
    String id, ownersId;
    int level, cost, upgradeCost, power;

    @Override
    public boolean isValid() {
        return !id.isEmpty();
    }

    @Override
    public Card fromDto() {
        return Card.builder().id(id).build();
    }

    @Override
    public IOutputDto<Card> toDto(Card card) {
        return FightCardDto.builder()
                .cardType((CardTypeDto) new CardTypeDto().toDto(card.getCardType()))
                .id(card.getId())
                .ownersId(card.getPlayer().getId())
                .level(card.getLevel())
                .power(card.getPower())
                .build();
    }
}
