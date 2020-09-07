package clash.back.domain.dto;

import clash.back.domain.entity.Age;
import clash.back.domain.entity.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardTypeDto implements IOutputDto<CardType> {
    String id;
    String name;
    int chivalryCost, knowledgeCost, orderNo, power, createXP;

    @Override
    public IOutputDto<CardType> toDto(CardType cardType) {
        return CardTypeDto.builder()
                .id(cardType.getId())
                .chivalryCost(cardType.getChivalryCost())
                .knowledgeCost(cardType.getKnowledgeCost())
                .createXP(cardType.getCreateXP())
                .orderNo(cardType.getOrderNo())
                .power(cardType.getPower())
                .build();
    }

    public IOutputDto<CardType> toAgeAdoptedDto(CardType cardType, Age age) {
        return CardTypeDto.builder()
                .id(cardType.getId())
                .chivalryCost(age.adoptedExpense(cardType.getChivalryCost()))
                .knowledgeCost(age.adoptedExpense(cardType.getKnowledgeCost()))
                .createXP(cardType.getCreateXP())
                .orderNo(cardType.getOrderNo())
                .power(cardType.getPower())
                .build();
    }
}
