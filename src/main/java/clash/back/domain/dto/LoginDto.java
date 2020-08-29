package clash.back.domain.dto;

import clash.back.domain.entity.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto implements IInputDto<Player> {

    String username;
    String password;

    @Override
    public boolean isValid() {
        return password.length() >= 5 && !username.isEmpty();
    }

    @Override
    public Player fromDto() {
        return Player.builder().username(username).password(password).build();
    }
}
