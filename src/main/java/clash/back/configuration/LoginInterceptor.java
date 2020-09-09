package clash.back.configuration;

import clash.back.controller.GameController;
import clash.back.controller.PlayerController;
import clash.back.domain.entity.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

public class LoginInterceptor implements ChannelInterceptor {
    private static final Logger logger = LogManager.getLogger(ChannelInterceptor.class);

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
                break;
            case DISCONNECT:
                try {
                    logger.info("--user " + user.getPlayer().getUsername() + " disconnected. ");
                } catch (NullPointerException e) {
                    logger.info("-- null player, disconnect interceptor");
                }
                break;
            default:
        }
    }

    void handleUserConnect(StompHeaderAccessor wrap, StompPrincipal user) {
        try {
            Player playerByUsername = playerController.getPlayerByUsernameIgnoreCase(wrap.getPasscode().trim());
            user.setPlayer(playerByUsername);
            wrap.setUser(user);
            gameController.addPrincipal(user);
            logger.info("-- user " + playerByUsername.getUsername() + " connected.");
        } catch (Exception e) {
            logger.error("-- error in handle user connect, from interceptor");
        }
    }
}
