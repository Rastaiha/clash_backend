package clash.back.domain.dto;

import clash.back.domain.entity.FightStage;

public class FightTimerDto implements IOutputDto<Integer> {
    int remained;
    String message;
    FightStage fightStage;

    public IOutputDto<Integer> toDto(Integer remained, FightStage fightStage) {
        this.remained = remained;
        this.fightStage = fightStage;
        return this;
    }

    public IOutputDto<Integer> toDto(Integer remained, String message, FightStage fightStage) {
        this.message = message;
        this.remained = remained;
        this.fightStage = fightStage;
        return this;
    }

    @Override
    public IOutputDto<Integer> toDto(Integer remained) {
        this.remained = remained;
        return this;
    }
}
