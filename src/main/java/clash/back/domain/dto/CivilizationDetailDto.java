package clash.back.domain.dto;

import clash.back.domain.entity.Civilization;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CivilizationDetailDto implements IOutputDto<Civilization> {
    String id, name;
    AgeDto age;
    int knowledge, chivalry, XP;
    TownHallDto townHall;


    @Override
    public IOutputDto<Civilization> toDto(Civilization civilization) {
        return CivilizationDetailDto.builder()
                .id(civilization.getId())
                .name(civilization.getName())
                .age((AgeDto) new AgeDto().toDto(civilization.getAge()))
                .chivalry(civilization.getChivalry())
                .knowledge(civilization.getKnowledge())
                .XP(civilization.getXP())
                .townHall((TownHallDto) new TownHallDto().toDto(civilization.getTownHall()))
                .build();
    }
}
