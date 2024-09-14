package com.chatbox.dto.component;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Profile {
    private String id;
    private String fullName;
    private String status;
    private int isFriend;
    private String code;
}
