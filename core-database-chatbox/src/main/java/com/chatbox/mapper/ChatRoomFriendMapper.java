package com.chatbox.mapper;

import com.chatbox.entity.ChatRoom;

import java.sql.ResultSet;

public class ChatRoomFriendMapper implements RowMapper<ChatRoom>{

    @Override
    public ChatRoom mapRow(ResultSet resultSet) {
        try {
            return ChatRoom.builder()
                    .code(resultSet.getString("code"))
                    .idFriendOrGroup(resultSet.getString("idFriendOrGroup"))
                    .nameChatRoom(resultSet.getString("nameChatRoom"))
                    .isFriend(resultSet.getObject("isFriend", Integer.class))
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}
