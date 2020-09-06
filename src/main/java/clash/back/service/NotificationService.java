package clash.back.service;

import clash.back.domain.dto.ScheduleNotificationDto;
import clash.back.domain.entity.Notification;
import clash.back.domain.entity.NotificationStatus;
import clash.back.domain.entity.Player;
import clash.back.handler.GlobalNotificationHandler;
import clash.back.repository.NotificationRepository;
import clash.back.repository.PlayerRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Setter
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    PlayerRepository playerRepository;

    GlobalNotificationHandler handler;

    public void updateNotificationStatus(Notification notification) {
        notificationRepository.save(notification);

    }

    public Iterable<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public void sendToAll(ScheduleNotificationDto dto) {
        if (dto.getTime() == 0) handler.sendToAll(dto.getType(), dto.getMessage());
        else handler.sendToAll(dto.getType(), dto.getMessage(), dto.getTime());
    }

    public List<Notification> getNotifications(Player player) {
        List<Notification> notifications = notificationRepository.findByRecipientAndStatusNot(player.getId(), NotificationStatus.PENDING);
        List<Notification> toReturn = new ArrayList<>();
        Collections.copy(toReturn, notifications);
        notifications.stream().filter(notification -> notification.getStatus().equals(NotificationStatus.SENT))
                .forEach(notification -> notificationRepository.save(notification.toBuilder()
                        .deliveredTime(new Date().getTime())
                        .status(NotificationStatus.SEEN).build()));

        return toReturn;
    }
}
