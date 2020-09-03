package clash.back.domain.dto;

import clash.back.domain.entity.Notification;
import clash.back.domain.entity.NotificationType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto implements IOutputDto<Notification> {
    String id, message;
    NotificationType notificationType;

    @Override
    public IOutputDto<Notification> toDto(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .notificationType(notification.getNotificationType())
                .message(notification.getMessage())
                .build();
    }
}
