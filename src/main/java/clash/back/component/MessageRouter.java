package clash.back.component;

import clash.back.controller.GameController;
import clash.back.domain.dto.IOutputDto;
import clash.back.domain.entity.Civilization;
import clash.back.domain.entity.Player;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageRouter {

    @Autowired
    public static SimpMessagingTemplate template;

    @Autowired
    public static GameController gameController;

    public void sendToCivilization(Civilization civilization, IOutputDto message) {
        Gson gson = new Gson();

        civilization.getPlayers().stream()
                .forEach(player -> gameController.getActivePrincipals().stream()
                        .filter(principal -> principal.getPlayer().getId().equals(player.getId()))
                        .forEach(principal -> template.convertAndSendToUser(principal.getName(), "/queue/team", gson.toJson(message))));
    }

    public void sendToAll(IOutputDto message) {
        Gson gson = new Gson();
        gameController.getActivePrincipals().stream()
                .forEach(principal -> template.convertAndSendToUser(principal.getName(), "/queue/team", gson.toJson(message)));
    }

    public void sendToSpecificPlayer(Player player, IOutputDto message) {
        Gson gson = new Gson();
        gameController.getActivePrincipals().stream()
                .filter(principal -> principal.getPlayer().getId().equals(player.getId()))
                .findAny().
                ifPresent(principal -> template.convertAndSendToUser(principal.getName(), "/queue/team", gson.toJson(message)));
    }


}
