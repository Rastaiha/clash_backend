package clash.back.handler;

import clash.back.domain.entity.*;
import clash.back.service.NotificationService;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
public class GlobalNotificationHandler extends DefaultHandler {
    static {
        RELOAD_INTERVAL = 10000L;
    }

    List<NotificationHandler> handlerList;
    NotificationService notificationService;

    @Override
    public void init() {
        super.init();
        handlerList = new ArrayList<>();
    }

    @Override
    void handle() {// todo: check concurrent remove and save
        handlerList.stream().filter(notificationHandler -> notificationHandler.getNotification().getStatus().equals(NotificationStatus.SENT))
                .forEach(notificationHandler -> notificationService.updateNotificationStatus(notificationHandler.getNotification()));
        handlerList.removeIf(notificationHandler -> notificationHandler.getNotification().getStatus().equals(NotificationStatus.SENT));
        handlerList.forEach(NotificationHandler::handle);
    }

    public void send(Player player, NotificationType notificationType, String message, long date) {

        Notification notification = Notification.builder()
                .notificationType(notificationType)
                .message(message)
                .createdTime(new Date().getTime())
                .scheduledTime(date)
                .id(UUID.randomUUID().toString())
                .recipient(player)
                .status(NotificationStatus.PENDING)
                .build();

        notificationService.updateNotificationStatus(notification);
        handlerList.add(new NotificationHandler(notification));
        logger.info("Notification scheduled for: " + new Date(notification.getScheduledTime()) + " created at: " + new Date(notification.getCreatedTime()));
    }

    public void send(Player player, NotificationType notificationType, String message) {
        send(player, notificationType, message, new Date().getTime());
    }

    public void sendToTeam(Civilization civilization, NotificationType notificationType, String message) {
        civilization.getPlayers().forEach(player -> send(player, notificationType, message));
    }

    public void sendToTeam(Civilization civilization, NotificationType notificationType, String message, long date) {
        civilization.getPlayers().forEach(player -> send(player, notificationType, message, date));
    }

    public void sendToAll(NotificationType notificationType, String message) {
        notificationService.getAllPlayers().forEach(player -> send(player, notificationType, message));
    }

    public void sendToAll(NotificationType notificationType, String message, long date) {
        notificationService.getAllPlayers().forEach(player -> send(player, notificationType, message, date));
    }
}
