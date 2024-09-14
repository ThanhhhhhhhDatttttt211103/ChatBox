package com.chatbox.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountDto {
    private String idUser;
    private String username;
    private String password;
}
