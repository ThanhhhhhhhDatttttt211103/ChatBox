package com.chatbox.dto.component;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FriendRequest {
    private String idUser;
    private String idFriend;
    private String code;
    private int type;
}
