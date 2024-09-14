package com.chatbox.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendDto {
    private String idUser;
    private String friend;
    private String code;
    private int isEnable;
}
