package com.chatbox.entity;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageCore {
    private Long id;
    private String sender;
    private String received;
    private String content;
    private String code;
    private Timestamp sendTime;
    private int isEnable;
    private int type;
    private Long messageEdt;
}
