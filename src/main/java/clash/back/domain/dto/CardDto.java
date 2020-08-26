package clash.back.domain.dto;

import clash.back.domain.entity.Card;
import clash.back.domain.entity.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto implements IOutputDto<Card> {
    CardType cardType;
    int level, cost, upgradeCost;
    @Override
    public IOutputDto<Card> toDto(Card card) {
        return CardDto.builder()
                .cardType(card.getCardType())
                .cost(card.getCost())
                .level(card.getLevel())
                .upgradeCost(card.getLevel())
                .build();
    }
}
