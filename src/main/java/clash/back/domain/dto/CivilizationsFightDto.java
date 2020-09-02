package clash.back.domain.dto;

import clash.back.domain.entity.Civilization;

import java.util.List;

public class CivilizationsFightDto implements IInputDto<Civilization> {
    List<PlayerDto> players;

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public Civilization fromDto() {
        return null;
    }
}
