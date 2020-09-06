package clash.back.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    String id;

    long createdTime, scheduledTime, sendTime, deliveredTime;
    NotificationStatus status;
    NotificationType notificationType;
    String message;

    @ManyToOne(fetch = FetchType.LAZY)
    Player recipient;
}
