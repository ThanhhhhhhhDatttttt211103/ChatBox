package com.chatbox.mapper;

import com.chatbox.entity.NotificationDisplay;

import java.sql.ResultSet;

public class NotificationDislayMapper implements RowMapper<NotificationDisplay> {

    @Override
    public NotificationDisplay mapRow(ResultSet resultSet) {
        try {
            return NotificationDisplay.builder()
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
                    .nameChatRoom(resultSet.getString("nameChatRoom"))
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}
