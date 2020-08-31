package clash.back.domain.dto;

public class TimerDto implements IOutputDto<Integer> {
    int remained;
    String message;

    @Override
    public IOutputDto<Integer> toDto(Integer remained) {
        return this.toDto(remained, null);
    }

    public IOutputDto<Integer> toDto(Integer remained, String message) {
        this.message = message;
        this.remained = remained;
        return this;
    }
}
