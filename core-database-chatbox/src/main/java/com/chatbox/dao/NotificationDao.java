package com.chatbox.dao;

import com.chatbox.entity.NotificationCore;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;


@Repository
public class NotificationDao extends AbstractDao<NotificationCore> {

    public NotificationDao(DataSource dataSource) {
        super(dataSource);
    }

    public int countNotificationUnread(String idUser) {
        String procName = "{call proc_CountNotifications(?, ?)}";
        return count(procName, idUser);
    }

    public String updateNotificationUnread(String idUser) {
        String procName = "{call proc_UpdateNotificationUnread(?)}";
        String response =  update(procName, idUser);
        return  response;
    }
}
