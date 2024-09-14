package com.chatbox.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProfileCore {
    private String id;
    private String fullName;
    private String status;
    private int isFriend;
    private String code;
}