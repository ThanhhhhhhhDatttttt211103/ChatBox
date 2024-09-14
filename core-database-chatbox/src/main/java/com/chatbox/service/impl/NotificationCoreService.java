package com.chatbox.service.impl;

import com.chatbox.entity.NotificationDisplay;
import com.chatbox.dao.NotificationDao;
import com.chatbox.dao.NotificationDisplayDao;
import com.chatbox.service.INotificationCoreService;
import com.chatbox.util.APIResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationCoreService implements INotificationCoreService {

    private NotificationDao notificationRepository;

    private NotificationDisplayDao notificationDisplayRepository;
    @Override
    public int countNotificationUnread(String idUser) {
        try {
            log.info("Begin countNotificationUnread with idUser: {}", idUser);
            int countNotificationUnread = notificationRepository.countNotificationUnread(idUser);
            log.info("End countNotificationUnread SUCCESS with count = {}", countNotificationUnread);
            return countNotificationUnread;
        } catch (Exception e) {
            log.info("Exception in countNotificationUnread");
            log.error("Exception in countNotificationUnread", e);
            return 0;
        }
    }

    @Override
    public List<NotificationDisplay> getNotifications(String idUser) {
        try {
            log.info("Begin getNotifications with idUser: {}", idUser);
            List<NotificationDisplay> listNotificationDisplay = notificationDisplayRepository.getNotifications(idUser);
            if (listNotificationDisplay.isEmpty()) {
                log.info("Can't find any annotation");
                return new ArrayList<>();
            }
            log.info("End getNotifications SUCCESS with count = {}", listNotificationDisplay.size());
            return listNotificationDisplay;
        } catch (Exception e) {
            log.info("Exception in getNotifications");
            log.error("Exception in getNotifications", e);
            return new ArrayList<>();
        }
    }

    @Override
    public String updateNotificationUnread(String idUser) {
        try {
            log.info("Begin updateNotificationUnread with idUser: {}", idUser);
            String response = notificationRepository.updateNotificationUnread(idUser);
            if (response.equals(APIResponse.ERROR)) {
                log.info("Can't update notification unread");
                return APIResponse.ERROR;
            }
            log.info("End updateNotificationUnread SUCCESS");
            return APIResponse.SUCCESS;
        } catch (Exception e) {
            log.info("Exception in updateNotificationUnread");
            log.error("Exception in updateNotificationUnread", e);
            return APIResponse.NOT_FOUND;
        }
    }
}
