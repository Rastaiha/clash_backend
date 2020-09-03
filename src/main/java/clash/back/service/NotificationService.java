package clash.back.service;

import clash.back.domain.dto.ScheduleNotificationDto;
import clash.back.domain.entity.Notification;
import clash.back.domain.entity.Player;
import clash.back.handler.GlobalNotificationHandler;
import clash.back.repository.NotificationRepository;
import clash.back.repository.PlayerRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
