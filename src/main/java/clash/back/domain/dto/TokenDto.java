package clash.back.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto implements IOutputDto<String> {

    String token;

    @Override
    public IOutputDto<String> toDto(String token) {
        return TokenDto.builder().token(token).build();
    }
}
