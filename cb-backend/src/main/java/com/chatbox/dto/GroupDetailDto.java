package com.chatbox.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupDetailDto {
    private String idGroup;
    private String idUser;
    private int isEnable;
}
