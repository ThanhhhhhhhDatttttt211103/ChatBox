package com.chatbox.messagejob.comsumer;

import com.chatbox.messagejob.dto.NotificationDto;
import com.chatbox.messagejob.dto.component.NotificationDisplay;
import com.chatbox.messagejob.entity.Notification;
import com.chatbox.messagejob.mapper.NotificationMapper;
import com.chatbox.messagejob.service.IGroupService;
import com.chatbox.messagejob.service.INotificationService;
import com.chatbox.messagejob.service.IUserService;
import com.chatbox.messagejob.util.IDUtil;
import com.chatbox.messagejob.util.RabbitUtil;
import com.chatbox.publisher.RabbitMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationConsumer {
    @Autowired
    private INotificationService notificationService;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @Autowired
    private IUserService userService;

    @Autowired
    private IGroupService groupService;

    @RabbitListener(queues = "${rabbitmq.queue.notification.request}")
    public void receive(NotificationDto notificationDto) {
        log.info("Begin receive notification from queue {}", notificationDto);
        responseNotification(notificationDto);
        log.info("End receive notification from queue");
    }

    private void responseNotification(NotificationDto notificationDto) {
        try {
            log.info("Begin responseNotification with {}", notificationDto);
            Notification notificationSaved = notificationService.save(NotificationMapper.mapToNotification(notificationDto));
            String nameChatRoom;
            if (notificationDto.getIdChatRoom().contains(IDUtil.GROUP)) {
                nameChatRoom = groupService.getGroupName(notificationDto.getIdChatRoom());
            } else {
                nameChatRoom = userService.getNameUser(notificationDto.getIdChatRoom());
            }
            if (notificationSaved == null) {
                log.info("Can't save notification");
                return;
            }
            NotificationDisplay notificationDisplay = NotificationMapper.mapToNotificationDisplay(notificationSaved);
            notificationDisplay.setNameChatRoom(nameChatRoom);
            rabbitMQProducer.sendMessage(RabbitUtil.ROUTING_KEY_NOTIFICATION_RESPONSE, notificationDisplay);
            log.info("Send notification to queue response {}", RabbitUtil.ROUTING_KEY_NOTIFICATION_RESPONSE);
        } catch (Exception e) {
            log.info("Exception in responseNotification");
            log.error("Exception in responseNotification", e);
        }
    }
}
