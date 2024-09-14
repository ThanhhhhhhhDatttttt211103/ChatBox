package com.chatbox.service.impl;

import com.chatbox.repository.NotificationRepository;
import com.chatbox.service.INotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService implements INotificationService {

    private NotificationRepository notificationRepository;

}
