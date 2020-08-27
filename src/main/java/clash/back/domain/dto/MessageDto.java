package clash.back.domain.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto implements IOutputDto<String> {

    String message;

    @Override
    public IOutputDto<String> toDto(String s) {
        return MessageDto.builder().message(s).build();
    }
}
