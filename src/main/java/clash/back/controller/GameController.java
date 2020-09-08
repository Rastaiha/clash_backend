package clash.back.controller;

import clash.back.component.MessageRouter;
import clash.back.configuration.LoginInterceptor;
import clash.back.configuration.StompPrincipal;
import clash.back.domain.dto.MapDto;
import clash.back.domain.dto.PlayerMovementDto;
import clash.back.domain.entity.Map;
import clash.back.handler.DefaultHandler;
import clash.back.handler.GlobalFightingHandler;
import clash.back.handler.MapHandler;
import clash.back.service.GameService;
import clash.back.service.PlayerService;
import clash.back.util.pathFinding.GameRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private static final Logger logger = LogManager.getLogger(GameController.class);

    Set<StompPrincipal> activePrincipals = new HashSet<>();

    @Autowired
    GameService gameService;

    @Autowired
    PlayerService playerService;

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    MessageRouter messageRouter;

    public void init() {
        LoginInterceptor.gameController = this;
        MessageRouter.gameController = this;
        MessageRouter.template = this.template;
        DefaultHandler.setMessageRouter(this.messageRouter);
        DefaultHandler.setGameRouter(new GameRouter(gameService.getMap()));
        MapHandler mapHandler = new MapHandler(gameService.getMap());
        GlobalFightingHandler fightingHandler = new GlobalFightingHandler();
        fightingHandler.init();
        gameService.setMapHandler(mapHandler);
        gameService.setFightingHandler(fightingHandler);
        fightingHandler.setGameService(gameService);
    }

    public void addPrincipal(StompPrincipal principal) {
        playerService.updateLastSeen(principal.getPlayer());
        activePrincipals.add(principal);
    }

    public void removePrincipal(StompPrincipal principal) {
        if (principal.getPlayer() == null) {
            logger.warn("principal doesn't have any user");
            return;
        }
        logger.info("-- disconnect event happened, user " + principal.getPlayer().getUsername() + " disconnected.");
        Optional<StompPrincipal> any = activePrincipals.stream().filter(principal1 -> principal1.getName().equalsIgnoreCase(principal.getName())).findAny();
        any.ifPresent(value -> activePrincipals.remove(value));
    }

    public Set<StompPrincipal> getActivePrincipals() {
        return activePrincipals;
    }

    @GetMapping("/map")
    public ResponseEntity<MapDto> getMapDetails() {
        Map map = gameService.getMap();
        return ResponseEntity.ok((MapDto) new MapDto().toDto(map));
    }

    @GetMapping
    public ResponseEntity<List<PlayerMovementDto>> getPlayersLocations() {
        return ResponseEntity.ok(gameService.getPlayers().stream()
                .map(player -> (PlayerMovementDto) new PlayerMovementDto().toDto(player)).collect(Collectors.toList()));
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        if (event.getUser() != null)
            removePrincipal((StompPrincipal) event.getUser());
        else logger.info("-- disconnect event happened, user is null");
    }

}
