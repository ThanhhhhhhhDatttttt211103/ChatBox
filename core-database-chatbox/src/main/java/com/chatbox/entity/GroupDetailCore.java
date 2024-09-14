package com.chatbox.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupDetailCore {
    private String idGroup;
    private String idUser;
}
