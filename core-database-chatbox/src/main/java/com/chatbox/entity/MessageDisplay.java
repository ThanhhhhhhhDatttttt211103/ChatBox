package com.chatbox.entity;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MessageDisplay {
    private Long id;
    private String sender;
    private String received;
    private String content;
    private String code;
    private Timestamp sendTime;
    private int isEnable;
    private int type;
    private Long messageEdt;
    private String senderName;
}
