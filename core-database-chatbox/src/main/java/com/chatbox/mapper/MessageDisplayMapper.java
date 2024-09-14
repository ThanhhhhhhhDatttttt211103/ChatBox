package com.chatbox.mapper;

import com.chatbox.entity.MessageDisplay;

import java.sql.ResultSet;

public class MessageDisplayMapper implements RowMapper<MessageDisplay>{
    @Override
    public MessageDisplay mapRow(ResultSet resultSet) {
        try {
            return MessageDisplay.builder()
                    .id(resultSet.getLong("id"))
                    .sender(resultSet.getString("sender"))
                    .received(resultSet.getString("received"))
                    .content(resultSet.getString("content"))
                    .code(resultSet.getString("code"))
                    .sendTime(resultSet.getTimestamp("sendTime"))
                    .isEnable(resultSet.getInt("isEnable"))
                    .type(resultSet.getInt("type"))
                    .messageEdt(resultSet.getLong("messageEdt"))
                    .senderName(resultSet.getString("senderName"))
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}
