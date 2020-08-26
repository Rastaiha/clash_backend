package clash.back.domain.dto;

public interface IInputDto<E> {
    boolean isValid();

    E fromDto();
}
