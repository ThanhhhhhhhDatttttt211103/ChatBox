package com.chatbox.consumer;

import com.chatbox.dto.component.NotificationDisplayDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "${rabbitmq.queue.notification.response}")
    public void receiveNotification(NotificationDisplayDto notificationDisplay) {
        try {
            log.info("Begin receiveNotification from queue reponse");
            log.info("Send notification to received");
            messagingTemplate.convertAndSendToUser(
                    notificationDisplay.getReceived(), "/queue/notification", notificationDisplay);
            log.info("End receiveNotification");
        } catch (Exception e) {
            log.info("Exception in receiveNotification", e);
            log.error("Exception in receiveNotification", e);
        }

    }
}
