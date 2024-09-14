package com.chatbox.messagejob.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageDto {
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
