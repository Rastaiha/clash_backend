package clash.back.domain.dto;

import clash.back.domain.entity.Notification;
import clash.back.domain.entity.NotificationType;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleNotificationDto implements IInputDto<Notification> {
    NotificationType type;
    String message;
    long time;

    @Override
    public boolean isValid() {
        return !message.isEmpty();
    }

    @Override
    public Notification fromDto() {
        return Notification.builder().notificationType(type).message(message).scheduledTime(time).build();
    }
}
