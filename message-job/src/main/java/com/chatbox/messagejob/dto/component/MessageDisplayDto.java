package com.chatbox.messagejob.dto.component;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MessageDisplayDto {
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
