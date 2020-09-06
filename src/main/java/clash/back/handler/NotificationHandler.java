package clash.back.handler;

import clash.back.domain.dto.NotificationDto;
import clash.back.domain.entity.Notification;
import clash.back.domain.entity.NotificationStatus;
import clash.back.util.Settings;
import lombok.Getter;

import java.util.Date;

@Getter
public class NotificationHandler extends DefaultHandler {
    Notification notification;

    public NotificationHandler(Notification notification) {
        this.notification = notification;
        this.init();
    }

    @Override
    void handle() {
        if (isLate(notification.getSendTime()) && notification.getStatus().equals(NotificationStatus.PENDING))
            send();
    }

    @Override
    public void init() {

    }

    private void send() {
        notification.setStatus(NotificationStatus.SENT);
        notification.setSendTime(new Date().getTime());
        messageRouter.sendToSpecificPlayer(notification.getRecipient(), new NotificationDto().toDto(notification), Settings.WS_NOTIFICATION_DEST);
        logger.info("Notification sent to: " + notification.getRecipient().getUsername() + " at: " + new Date(notification.getSendTime()));
    }

    private boolean isLate(long date) {
        return date <= new Date().getTime();
    }
}
