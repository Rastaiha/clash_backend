package clash.back.configuration;

import clash.back.controller.GameController;
import clash.back.controller.PlayerController;
import clash.back.domain.entity.Player;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

public class LoginInterceptor implements ChannelInterceptor {
    public static GameController gameController;
    public static PlayerController playerController;

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor wrap = StompHeaderAccessor.wrap(message);
        StompCommand command = wrap.getCommand();
        StompPrincipal user = (StompPrincipal) wrap.getUser();

        switch (command != null ? command : StompCommand.ABORT) {
            case CONNECT:
                handleUserConnect(wrap, user);
            case DISCONNECT:
                handleUserDisconnect(wrap, user);
            default:
        }
    }

    void handleUserConnect(StompHeaderAccessor wrap, StompPrincipal user) {
        Player playerByUsername = playerController.getPlayerByUsername(wrap.getPasscode());
        user.setPlayer(playerByUsername);
        wrap.setUser(user);
        gameController.addPrincipal(user);
    }

    void handleUserDisconnect(StompHeaderAccessor wrap, StompPrincipal user) {
        gameController.removePrincipal(user);
    }
}
