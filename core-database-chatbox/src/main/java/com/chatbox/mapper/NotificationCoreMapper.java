package com.chatbox.mapper;

import com.chatbox.entity.NotificationCore;
import com.chatbox.entity.NotificationDisplay;

import java.sql.ResultSet;

public class NotificationCoreMapper implements RowMapper<NotificationCore> {

    @Override
    public NotificationCore mapRow(ResultSet resultSet) {
        try {
            return NotificationCore.builder()
                    .id(resultSet.getLong("id"))
                    .sender(resultSet.getString("sender"))
                    .content(resultSet.getString("content"))
                    .received(resultSet.getString("received"))
                    .sendTime(resultSet.getTimestamp("sendTime"))
                    .idChatRoom(resultSet.getString("idChatRoom"))
                    .type(resultSet.getInt("type"))
                    .isRead(resultSet.getInt("isRead"))
                    .isEnable(resultSet.getInt("isEnable"))
                    .isAgreed(resultSet.getObject("isAgreed", Integer.class))
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}
