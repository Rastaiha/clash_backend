package clash.back.domain.dto;

import clash.back.domain.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto implements IOutputDto<Card> {
    CardTypeDto cardType;
    int level, cost, upgradeCost;
    @Override
    public IOutputDto<Card> toDto(Card card) {
        return CardDto.builder()
                .cardType((CardTypeDto) new CardTypeDto().toDto(card.getCardType()))
                .level(card.getLevel())
                .upgradeCost(card.getLevel())
                .build();
    }
}
