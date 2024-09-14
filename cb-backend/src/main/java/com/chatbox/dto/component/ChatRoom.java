package com.chatbox.dto.component;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom {
    private String code;
    private String idFriendOrGroup;
    private String lastMessage;
    private String nameChatRoom;
    private String sender;
    private Timestamp lastSendTime;
    private Integer isFriend;
}
