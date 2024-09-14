package com.chatbox.dao;

import com.chatbox.entity.NotificationDisplay;
import com.chatbox.mapper.NotificationDislayMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class NotificationDisplayDao extends AbstractDao<NotificationDisplay> {

    public NotificationDisplayDao(DataSource dataSource) {
        super(dataSource);
    }
    public List<NotificationDisplay> getNotifications(String idUser) {
        String procName = "{call proc_GetNotifications(?, ?)}";
        List<NotificationDisplay> listNotification = query(procName, new NotificationDislayMapper(), idUser);
        return listNotification;
    }
}
