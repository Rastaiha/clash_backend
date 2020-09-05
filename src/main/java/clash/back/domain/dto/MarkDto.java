package clash.back.domain.dto;

public class MarkDto implements IInputDto<Boolean> {
    boolean mark;

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Boolean fromDto() {
        return mark;
    }
}
