package com.chatbox.entity;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChatRoom {
    private String code;
    private String idFriendOrGroup;
    private String nameChatRoom;
    private String sender;
    private String lastMessage;
    private Timestamp lastSendTime;
    private Integer isFriend;
}
