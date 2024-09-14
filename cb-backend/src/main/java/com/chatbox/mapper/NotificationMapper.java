package com.chatbox.mapper;

import com.chatbox.dto.NotificationDto;
import com.chatbox.dto.component.NotificationDisplayDto;
import com.chatbox.entity.Notification;
import com.chatbox.entity.NotificationCore;
import com.chatbox.entity.NotificationDisplay;

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

    public static NotificationDto mapToNotificationDto(NotificationCore notification) {
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


    public static NotificationDisplayDto mapToNotificationDisplay(Notification notification) {
        return NotificationDisplayDto.builder()
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

    public static NotificationDisplayDto mapToNotificationDisplay(NotificationDisplay notification) {
        return NotificationDisplayDto.builder()
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
                .nameChatRoom(notification.getNameChatRoom())
                .build();
    }
}
