package clash.back.domain.dto;

public interface IOutputDto<E> {
    IOutputDto<E>  toDto(E e);
}
