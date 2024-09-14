package com.chatbox.mapper;

import com.chatbox.entity.MessageCore;

import java.sql.ResultSet;

public class MessageCoreMapper implements RowMapper<MessageCore>{
    @Override
    public MessageCore mapRow(ResultSet resultSet) {
        try {
            return MessageCore.builder()
                    .id(resultSet.getLong("id"))
                    .sender(resultSet.getString("sender"))
                    .received(resultSet.getString("received"))
                    .content(resultSet.getString("content"))
                    .code(resultSet.getString("code"))
                    .sendTime(resultSet.getTimestamp("sendTime"))
                    .isEnable(resultSet.getInt("isEnable"))
                    .type(resultSet.getInt("type"))
                    .messageEdt(resultSet.getLong("messageEdt"))
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}
