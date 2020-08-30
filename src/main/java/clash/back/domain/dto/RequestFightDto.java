package clash.back.domain.dto;

import clash.back.domain.entity.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestFightDto implements IInputDto<Player> {

    String username;

    @Override
    public boolean isValid() {
        return !username.isEmpty();
    }

    @Override
    public Player fromDto() {
        return Player.builder().username(username).build();
    }
}
