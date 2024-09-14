package com.chatbox.service;

import com.chatbox.entity.ChatRoom;

import java.util.List;

public interface IChatRoomService {
    List<ChatRoom> listChatRooms(String id);

    List<ChatRoom> listFriend(String id, String name);

    ChatRoom createChatRoomUI(String idUser, String idFriend);
}
