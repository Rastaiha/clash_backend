package clash.back.domain.dto;

import clash.back.domain.entity.Age;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgeDto implements IOutputDto<Age> {
    String name;
    int upgradeXP;

    @Override
    public IOutputDto<Age> toDto(Age age) {
        return AgeDto.builder().name(age.getName()).upgradeXP(age.getUpgradeXP()).build();
    }
}
