package com.chatbox.service.impl;

import com.chatbox.entity.ChatRoom;
import com.chatbox.dao.ChatRoomDao;
import com.chatbox.service.IChatRoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ChatRoomService implements IChatRoomService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private ChatRoomDao chatRoomRepository;

    @Override
    public List<ChatRoom> listChatRooms(String id) {
        try{
            log.info("Begin listChatRooms with id = {}", id);
            List<ChatRoom> chatRooms = chatRoomRepository.listChatRooms(id);
            if (chatRooms.isEmpty()) {
                log.info("Can't find any chat rooms");
                return new ArrayList<>();
            }
            log.info("End listChatrooms with count chatroom = {}",chatRooms.size());
            return chatRooms;
        } catch (Exception e) {
            log.info("Exception in listChatRooms");
            log.error("Exception in listChatRooms", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<ChatRoom> listFriend(String id, String name) {
        try {
            log.info("Begin listFriend with id = {} and name = {}", id, name);
            List<ChatRoom> chatRooms = chatRoomRepository.listFriend(id, name);
            if (chatRooms.isEmpty()) {
                log.info("Can't find any friends");
                return new ArrayList<>();
            }
            log.info("End listFriend with count friends = {}",chatRooms.size());
            return chatRoomRepository.listFriend(id, name);
        } catch (Exception e) {
            log.info("Exception in listFriend");
            log.error("Exception in listFriend", e);
            return new ArrayList<>();
        }
    }

    @Override
    public ChatRoom createChatRoomUI(String idUser, String idFriend) {
        try {
            log.info("Begin createChatRoomUI with id = {} and idUser = {}", idUser, idUser);
            ChatRoom chatRoom = chatRoomRepository.createChatRoomUI(idUser, idFriend);
            if (chatRoom == null) {
                log.info("Can't find any chat room");
                return new ChatRoom();
            }
            log.info("End createChatRoomUI with chatRoom = {}", chatRoom);
            return chatRoom;
        } catch (Exception e) {
            log.info("Exception in createChatRoomUI");
            log.error("Exception in createChatRoomUI", e);
            return new ChatRoom();
        }
    }
}
