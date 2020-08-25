package clash.back.configuration;


import clash.back.domain.entity.Player;
import lombok.Getter;
import lombok.Setter;

import java.security.Principal;

@Getter
@Setter
public class StompPrincipal implements Principal {

    private String name;
    private Player player;

    public StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}

