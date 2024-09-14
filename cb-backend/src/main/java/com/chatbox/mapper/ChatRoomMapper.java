package com.chatbox.mapper;

import com.chatbox.dto.component.ChatRoom;

public class ChatRoomMapper {

    public static ChatRoom mapToChatRoom(com.chatbox.entity.ChatRoom chatRoomDB) {
        return ChatRoom.builder()
                .code(chatRoomDB.getCode())
                .idFriendOrGroup(chatRoomDB.getIdFriendOrGroup())
                .nameChatRoom(chatRoomDB.getNameChatRoom())
                .sender(chatRoomDB.getSender())
                .lastMessage(chatRoomDB.getLastMessage())
                .lastSendTime(chatRoomDB.getLastSendTime())
                .isFriend(chatRoomDB.getIsFriend())
                .build();
    }
}
