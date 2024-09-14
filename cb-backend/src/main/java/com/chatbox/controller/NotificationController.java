package com.chatbox.controller;

import com.chatbox.dto.NotificationDto;
import com.chatbox.dto.component.NotificationDisplayDto;
import com.chatbox.entity.NotificationDisplay;
import com.chatbox.mapper.NotificationMapper;
import com.chatbox.publisher.RabbitMQProducer;
import com.chatbox.service.impl.NotificationCoreService;
import com.chatbox.util.RabbitMQ;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/notification")
@AllArgsConstructor
@Slf4j
public class NotificationController {
    private RabbitMQProducer rabbitMQService;
    private NotificationCoreService notificationCoreService;

    @MessageMapping("/handle")
    public void processNotification(@Payload NotificationDto notificationDto) {
        try {
            log.info("Begin processNotification");
            log.info("send {} to RabbitMQ SUCCESS", notificationDto);
            rabbitMQService.sendMessage(RabbitMQ.ROUTING_KEY_NOTIFICATION_REQUEST, notificationDto);
        }
        catch (Exception e) {
            log.info("Exception in  processNotification");
            log.error("Exception in  processNotification", e);
        }
    }

    @PostMapping("/count-notification-unread")
    public ResponseEntity<Integer> countNotificationUnread(@RequestParam("idUser") String idUser) {
        log.info("Begin countNotificationUnread API with idUser {}", idUser );
        long startTime = System.currentTimeMillis();
        int count = notificationCoreService.countNotificationUnread(idUser);
        log.info("End countNotificationUnread = {} in {} ms", count, System.currentTimeMillis() - startTime);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/get-all-notification")
    public ResponseEntity<List<NotificationDisplayDto>> getAllNotification(@RequestParam("idUser") String idUser) {
        log.info("Begin getAllNotification API with idUser {}", idUser);
        long startTime = System.currentTimeMillis();
        List<NotificationDisplay> listNotificationDisplay = notificationCoreService.getNotifications(idUser);
        List<NotificationDisplayDto> listNotificationDto = listNotificationDisplay.stream().map(NotificationMapper::mapToNotificationDisplay).toList();
        log.info("End getAllNotification with count = {} in {} ms", listNotificationDisplay.size(), System.currentTimeMillis() - startTime);
        return ResponseEntity.ok(listNotificationDto);
    }

    @PostMapping("/update-notification-unread")
    public ResponseEntity<String> updateNotificationUnread(@RequestParam("idUser") String idUser) {
        log.info("Begin updateNotificationUnread API with idUser {}", idUser);
        long startTime = System.currentTimeMillis();
        String response = notificationCoreService.updateNotificationUnread(idUser);
        log.info("End updateNotificationUnread = {} in {} ms", response, System.currentTimeMillis() - startTime);
        return ResponseEntity.ok(response);
    }
}
