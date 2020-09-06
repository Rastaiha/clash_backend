package clash.back.repository;

import clash.back.domain.entity.Notification;
import clash.back.domain.entity.NotificationStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, String> {
    List<Notification> findByRecipientAndNotStatus(String recipientId, NotificationStatus status);
}
