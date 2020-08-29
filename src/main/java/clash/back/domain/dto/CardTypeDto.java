package clash.back.domain.dto;

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
    int chivalryCost, chivalryValue, orderNo;

    @Override
    public IOutputDto<CardType> toDto(CardType cardType) {
        return CardTypeDto.builder()
                .id(cardType.getId())
                .chivalryCost(cardType.getChivalryCost())
                .chivalryValue(cardType.getChivalryValue())
                .name(cardType.getName())
                .orderNo(cardType.getOrderNo())
                .build();
    }
}
