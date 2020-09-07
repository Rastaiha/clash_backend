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
    String id;
    int level, upgradeChivalryCost, upgradeKnowledgeCost, power;
    boolean picked;

    @Override
    public IOutputDto<Card> toDto(Card card) {
        return CardDto.builder()
                .cardType((CardTypeDto) new CardTypeDto().toAgeAdoptedDto(card.getCardType(), card.getCivilization().getAge()))
                .id(card.getId())
                .level(card.getLevel())
                .upgradeChivalryCost(card.getUpgradeChivalryCost())
                .upgradeKnowledgeCost(card.getUpgradeKnowledgeCost())
                .power(card.getPower())
                .picked(card.getPlayer() != null)
                .build();
    }
}
