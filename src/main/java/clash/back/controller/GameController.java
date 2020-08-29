package clash.back.controller;

import clash.back.component.MessageRouter;
import clash.back.configuration.LoginInterceptor;
import clash.back.configuration.StompPrincipal;
import clash.back.handler.DefaultHandler;
import clash.back.handler.MapHandler;
import clash.back.service.GameService;
import clash.back.util.pathFinding.GameRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/game")
public class GameController {
    Set<StompPrincipal> activePrincipals = new HashSet<>();

    @Autowired
    GameService gameService;

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    MessageRouter messageRouter;

    public void init()  {
        LoginInterceptor.gameController = this;
        MessageRouter.gameController = this;
        MessageRouter.template = this.template;
        DefaultHandler.setMessageRouter(this.messageRouter);
        DefaultHandler.setGameRouter(new GameRouter(gameService.getMap()));
        MapHandler mapHandler = new MapHandler(gameService.getMap());
        mapHandler.init();
        gameService.setMapHandler(mapHandler);
    }

    public void addPrincipal(StompPrincipal principal) {
        activePrincipals.add(principal);
    }

    public void removePrincipal(StompPrincipal principal) {
        Optional<StompPrincipal> any = activePrincipals.stream().filter(principal1 -> principal1.getName().equalsIgnoreCase(principal.getName())).findAny();
        any.ifPresent(value -> activePrincipals.remove(value));
    }

    public Set<StompPrincipal> getActivePrincipals() {
        return activePrincipals;
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        removePrincipal((StompPrincipal) event.getUser());
    }

}
