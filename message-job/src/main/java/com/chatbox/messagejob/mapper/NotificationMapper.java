package com.chatbox.messagejob.mapper;


import com.chatbox.messagejob.dto.NotificationDto;
import com.chatbox.messagejob.dto.component.NotificationDisplay;
import com.chatbox.messagejob.entity.Notification;

public class NotificationMapper {
    public static Notification mapToNotification(NotificationDto notificationDto) {
        return Notification.builder()
                .id(notificationDto.getId())
                .sender(notificationDto.getSender())
                .content(notificationDto.getContent())
                .received(notificationDto.getReceived())
                .sendTime(notificationDto.getSendTime())
                .idChatRoom(notificationDto.getIdChatRoom())
                .type(notificationDto.getType())
                .isRead(notificationDto.getIsRead())
                .isEnable(notificationDto.getIsEnable())
                .isAgreed(notificationDto.getIsAgreed())
                .build();
    }

    public static NotificationDto mapToNotificationDto(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .sender(notification.getSender())
                .content(notification.getContent())
                .received(notification.getReceived())
                .sendTime(notification.getSendTime())
                .idChatRoom(notification.getIdChatRoom())
                .type(notification.getType())
                .isRead(notification.getIsRead())
                .isEnable(notification.getIsEnable())
                .isAgreed(notification.getIsAgreed())
                .build();
    }

    public static NotificationDisplay mapToNotificationDisplay(Notification notification) {
        return NotificationDisplay.builder()
                .id(notification.getId())
                .sender(notification.getSender())
                .content(notification.getContent())
                .received(notification.getReceived())
                .sendTime(notification.getSendTime())
                .idChatRoom(notification.getIdChatRoom())
                .type(notification.getType())
                .isRead(notification.getIsRead())
                .isEnable(notification.getIsEnable())
                .isAgreed(notification.getIsAgreed())
                .build();
    }
}
