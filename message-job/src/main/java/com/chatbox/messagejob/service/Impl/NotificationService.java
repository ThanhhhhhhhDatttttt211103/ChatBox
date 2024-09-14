package com.chatbox.messagejob.service.Impl;

import com.chatbox.messagejob.entity.Notification;
import com.chatbox.messagejob.repository.NotificationRepository;
import com.chatbox.messagejob.service.INotificationService;
import com.chatbox.messagejob.util.NotificationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationService implements INotificationService {

    private NotificationRepository notificationRepository;

    @Override
    public Notification save(Notification notification) {
        try {
            log.info("Begin save notification with {}", notification);
            Notification notifiSaved = notificationRepository.save(notification);
            if (notifiSaved.getId() == null) {
                log.info("Can't save notification");
                return NotificationUtil.NULL;
            }
            log.info("End save notification SUCCESS with {}", notifiSaved);
            return notifiSaved;
        } catch (Exception e) {
            log.info("Exception in save notification");
            log.error("Exception in save notification", e);
            return NotificationUtil.NULL;
        }
    }
}
