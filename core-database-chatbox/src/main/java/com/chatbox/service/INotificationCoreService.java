package com.chatbox.service;

import com.chatbox.entity.NotificationCore;
import com.chatbox.entity.NotificationDisplay;

import java.util.List;

public interface INotificationCoreService {
    public int countNotificationUnread(String idUser);
    public List<NotificationDisplay> getNotifications(String idUser);
    public String updateNotificationUnread(String idUser);
}
