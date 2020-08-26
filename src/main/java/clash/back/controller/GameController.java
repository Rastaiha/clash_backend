package clash.back.controller;

import clash.back.configuration.LoginInterceptor;
import clash.back.configuration.StompPrincipal;
import clash.back.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/game")
public class GameController {
    Set<Principal> activePrincipals = new HashSet<>();

    @Autowired
    GameService gameService;

    @Autowired
    SimpMessagingTemplate template;

    public void init() {
        LoginInterceptor.gameController = this;
    }

    public void addPrincipal(StompPrincipal principal) {
        activePrincipals.add(principal);
    }

    public void removePrincipal(StompPrincipal principal) {
        Optional<Principal> any = activePrincipals.stream().filter(principal1 -> principal1.getName().equalsIgnoreCase(principal.getName())).findAny();
        any.ifPresent(value -> activePrincipals.remove(value));
    }
}
