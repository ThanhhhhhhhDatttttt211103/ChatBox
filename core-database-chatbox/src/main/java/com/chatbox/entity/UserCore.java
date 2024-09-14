package com.chatbox.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserCore {
    private String id;
    private String fullname;
    private String status;
}
