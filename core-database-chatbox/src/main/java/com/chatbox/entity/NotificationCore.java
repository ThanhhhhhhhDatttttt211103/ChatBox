package com.chatbox.entity;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NotificationCore {
    private Long id;
    private String sender;
    private String content;
    private String received;
    private Timestamp sendTime;
    private String idChatRoom;
    private int type;
    private int isRead;
    private int isEnable;
    private Integer isAgreed;
}
