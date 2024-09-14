package com.chatbox.dao;

import com.chatbox.entity.ChatRoom;
import com.chatbox.mapper.ChatRoomCoreMapper;
import com.chatbox.mapper.ChatRoomFriendMapper;
import com.chatbox.util.MessageUtil;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ChatRoomDao extends AbstractDao<ChatRoom> {

    public ChatRoomDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<ChatRoom> listChatRooms (String idUser) {
        String procName = "{call proc_GetListChatRoom(?, ?)}";
        return query(procName, new ChatRoomCoreMapper(), idUser);
    }

    public List<ChatRoom> listFriend(String idUser, String name) {
        String procName = "{call proc_SearchFriend(?, ?, ?)}";
        List<ChatRoom> listFriend = query(procName, new ChatRoomFriendMapper(), idUser, name);
        return listFriend;
    }

    public ChatRoom createChatRoomUI(String idUser, String idFriend) {
        String procName = "{call proc_GetChatRoomWithFriend(?, ?, ?)}";
        if (idFriend.contains(MessageUtil.GROUP)) {
            procName = "{call proc_GetChatRoomWithGroup(?, ?, ?)}";
        }
        List<ChatRoom> listFriend = query(procName, new ChatRoomFriendMapper(), idUser, idFriend);
        if (listFriend.isEmpty()) {
            return null;
        }
        return listFriend.get(0);
    }
}
