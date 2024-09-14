package com.chatbox.dto.component;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageRequest {
    private String code;
    private String idUser;
    private int index;
}
