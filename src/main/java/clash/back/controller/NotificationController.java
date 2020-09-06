package clash.back.controller;

import clash.back.domain.dto.NotificationDto;
import clash.back.domain.dto.ScheduleNotificationDto;
import clash.back.handler.GlobalNotificationHandler;
import clash.back.service.NotificationService;
import clash.back.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public void init() {
        GlobalNotificationHandler handler = new GlobalNotificationHandler();
        notificationService.setHandler(handler);
        handler.init();
        handler.setNotificationService(notificationService);
    }

    @PostMapping
    public void schedule(@RequestBody ScheduleNotificationDto dto) {
        if (dto.isValid())
            notificationService.sendToAll(dto);
    }

    @GetMapping
    public ResponseEntity<List<NotificationDto>> getNotifications() {
        return ResponseEntity.ok(notificationService.getNotifications(userDetailsService.getUser()).stream()
                .map(notification -> (NotificationDto) new NotificationDto().toDto(notification)).collect(Collectors.toList()));
    }

}
