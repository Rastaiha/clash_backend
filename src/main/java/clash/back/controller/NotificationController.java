package clash.back.controller;

import clash.back.domain.dto.ScheduleNotificationDto;
import clash.back.handler.GlobalNotificationHandler;
import clash.back.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

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

}
